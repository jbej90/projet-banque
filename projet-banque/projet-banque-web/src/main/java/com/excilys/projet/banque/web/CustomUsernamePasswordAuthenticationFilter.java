package com.excilys.projet.banque.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public CustomUsernamePasswordAuthenticationFilter() {
		super();
		
		setUsernameParameter("username");
		setPasswordParameter("password");
		setFilterProcessesUrl("/login.form");
	}
}
