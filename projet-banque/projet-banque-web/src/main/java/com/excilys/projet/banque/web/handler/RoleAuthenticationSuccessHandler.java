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
import com.excilys.projet.banque.service.api.exception.UnknownLoginException;
import com.excilys.projet.banque.service.api.utils.SecurityUtils;
import com.excilys.projet.banque.web.utils.WebUtils;

/**
 * Classe de gestion des succès d'authentification. Cette classe est liée à Spring security via le fichier de contexte.
 * 
 * @author excilys
 * 
 */
public class RoleAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource
	private ClientService	clientService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		// Parcours des authorisation de cet utilisateur
		for (GrantedAuthority authority : authorities) {
			// S'il est admin, on le redirige directement sur la page admin
			if (authority.getAuthority().equals(SecurityUtils.ROLE_ADMIN)) {
				response.sendRedirect(response.encodeRedirectURL("admin/index" + WebUtils.URL_SUFFIX_PAGE));
				return;
				// S'il est un simple client
			}
			else if (authority.getAuthority().equals(SecurityUtils.ROLE_USER)) {
				// Récupère son identifiant client, et le stock en session
				try {
					int idClient;
					try {
						idClient = clientService.recupererClientId(authentication.getName());
						request.getSession().setAttribute("idClient", idClient);
						// TODO @Damien: définir les actions associées à la réception des exceptions
					}
					catch (UnknownLoginException e) {
						e.printStackTrace();
					}
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
				}

				// redirection vers la page d'accueil client
				response.sendRedirect(response.encodeRedirectURL("private/home" + WebUtils.URL_SUFFIX_PAGE));
				return;
			}
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
