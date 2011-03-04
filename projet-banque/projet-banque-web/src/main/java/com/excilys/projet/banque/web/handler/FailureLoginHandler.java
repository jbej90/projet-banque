package com.excilys.projet.banque.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.excilys.projet.banque.web.utils.MessageStack;

public class FailureLoginHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException) {
			MessageStack.getInstance(request).addError("Login ou mot de passe erroné");
		} else if (exception instanceof DisabledException) {
			MessageStack.getInstance(request).addError("Ce compte n'est pas activé");
		} else if (exception instanceof CredentialsExpiredException) {
			MessageStack.getInstance(request).addError("Votre session a expirée");
		} else {
			MessageStack.getInstance(request).addError("Connexion échouée");
		}
		
//		exception.printStackTrace();
		
		response.sendRedirect(response.encodeRedirectURL("login.htm"));
	}
}
