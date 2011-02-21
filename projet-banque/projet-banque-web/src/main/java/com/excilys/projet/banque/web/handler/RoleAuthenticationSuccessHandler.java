package com.excilys.projet.banque.web.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


public class RoleAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final static String ROLE_ADMIN = "ROLE_ADMIN";
	private final static String ROLE_USER = "ROLE_USER";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals(ROLE_ADMIN)) {
				response.sendRedirect(response.encodeRedirectURL("admin/index.html"));
				return;
			} else if (authority.getAuthority().equals(ROLE_USER)) {
				response.sendRedirect(response.encodeRedirectURL("private/home.html"));
				return;
			}
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
