package com.excilys.projet.banque.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;

@Component
public class PostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof UsernamePasswordAuthenticationFilter) {
			UsernamePasswordAuthenticationFilter filter = (UsernamePasswordAuthenticationFilter)bean;
			filter.setUsernameParameter("username");
			filter.setPasswordParameter("password");
			filter.setFilterProcessesUrl("/login.do");
			
		} else if (bean instanceof LogoutFilter) {
			LogoutFilter filter = (LogoutFilter)bean;
			filter.setFilterProcessesUrl("/logout.do");
		}
		return bean;
	}

}
