package com.excilys.projet.banque.web.handler;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.println("LOGOUT");
		System.err.println(authentication);
		System.err.println(request.getSession());
		
		Enumeration<String> e = request.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String aa = e.nextElement();
			System.err.println(aa+" = "+request.getSession().getAttribute(aa));
		}
	}

}
