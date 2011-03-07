package com.excilys.projet.banque.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

/**
 * Classe utilisée pour les configurations fines sur les beans après leur chargement. Elle est utilisée pour modifier le comportement par défaut de Spring security et utiliser des
 * champs de formulaire et des liens différents.
 * 
 * @author excilys
 * 
 */
@Component
public class PostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// si le bean est celui qui gère le formulaire d'authentification
		// on le reconfigure pour utiliser des champs et une URI de mapping différents
		if (bean instanceof UsernamePasswordAuthenticationFilter) {
			UsernamePasswordAuthenticationFilter filter = (UsernamePasswordAuthenticationFilter) bean;
			filter.setUsernameParameter("username");
			filter.setPasswordParameter("password");
			filter.setFilterProcessesUrl("/login.do");
		}
		// Si le bean est celui qui gère la déconnexion de l'utilisateur
		// on le reconfigure pour utiliser une URI de mapping différente
		else if (bean instanceof LogoutFilter) {
			LogoutFilter filter = (LogoutFilter) bean;
			filter.setFilterProcessesUrl("/logout.do");
		}
		return bean;
	}

}
