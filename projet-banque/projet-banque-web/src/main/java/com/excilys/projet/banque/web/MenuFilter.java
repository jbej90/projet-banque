package com.excilys.projet.banque.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.projet.banque.web.utils.MenuManager;

/**
 * Classe permettant de filtrer le contenu du menu et le l'item courant.
 * Elle indique juste l'URI actuelle au MenuManager qui va s'occuper du filtrage  
 */
public class MenuFilter implements Filter {
	private MenuManager menuManager;
	
	
	public MenuFilter() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		
		menuManager.setUri(req.getServletPath());
		
		// Continue la chaine d'exécution
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	
	
	public MenuManager getMenuManager() {
		return menuManager;
	}

	@Autowired
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}
}
