package com.excilys.projet.banque.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Classe permettant de gérer le peuplement du menu et la selection du menu courant.
 * Modèle basé sur celui de Stephane. Lui demander si besoin =) 
 * 
 * @author excilys
 *
 */
public class MenuFilter implements Filter {
	
	
	public MenuFilter() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		System.out.println(req.getServletPath());
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
