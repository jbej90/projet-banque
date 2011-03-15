package com.excilys.projet.banque.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
		// on le reconfigure pour utiliser des champs différents
		if (bean instanceof UsernamePasswordAuthenticationFilter) {
			UsernamePasswordAuthenticationFilter filter = (UsernamePasswordAuthenticationFilter) bean;
			filter.setUsernameParameter("username");
			filter.setPasswordParameter("password");
		}
		return bean;
	}

}
