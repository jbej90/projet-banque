package com.excilys.projet.banque.web.handler;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;


public class RoleAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final static String ROLE_ADMIN = "ROLE_ADMIN";
	private final static String ROLE_USER = "ROLE_USER";
	private static final String BASE_URL_SUFFIX = ".htm";
	
	@Resource
	private ClientService clientService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		// Parcours des authorisation de cet utilisateur
		for (GrantedAuthority authority : authorities) {
			// S'il est admin, on le redirige directement sur la page admin
			if (authority.getAuthority().equals(ROLE_ADMIN)) {
				response.sendRedirect(response.encodeRedirectURL("admin/index"+BASE_URL_SUFFIX));
				return;
			// S'il est un simple client
			} else if (authority.getAuthority().equals(ROLE_USER)) {
				// Récupère son identifiant client, et le stock en session
				try {
					int idClient = clientService.recupererClientId(authentication.getName());
					request.getSession().setAttribute("idClient", idClient);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				
				// redirection vers la page d'accueil client 
				response.sendRedirect(response.encodeRedirectURL("private/home"+BASE_URL_SUFFIX));
				return;
			}
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
