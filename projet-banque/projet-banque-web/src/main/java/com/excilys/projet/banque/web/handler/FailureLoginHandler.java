package com.excilys.projet.banque.web.handler;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.excilys.projet.banque.web.utils.MessageStack;

/**
 * Classe de gestion des erreurs d'authentification. Cette classe est liée à Spring security via le fichier de contexte
 * 
 * @author excilys
 * 
 */
public class FailureLoginHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//		System.out.println("LOGIN");
//		System.err.println(exception);
//		System.err.println(request.getSession());
//		
//		Enumeration<String> e = request.getSession().getAttributeNames();
//		while (e.hasMoreElements()) {
//			String aa = e.nextElement();
//			System.err.println(aa+" = "+request.getSession().getAttribute(aa));
//		}
		
		if (exception instanceof BadCredentialsException) {
			MessageStack.getInstance(request).addError("Login ou mot de passe erroné");
		}
		else if (exception instanceof DisabledException) {
			MessageStack.getInstance(request).addError("Ce compte n'est pas activé");
		}
		else if (exception instanceof CredentialsExpiredException) {
			MessageStack.getInstance(request).addError("Votre session a expirée");
		}
		else if (exception instanceof SessionAuthenticationException) {
			MessageStack.getInstance(request).addError("Votre compte est déjà utilisé sur un autre poste");
		}
		else {
			MessageStack.getInstance(request).addError("Connexion échouée");
			
			exception.printStackTrace();
		}

		response.sendRedirect(response.encodeRedirectURL("login.htm"));
	}
}
