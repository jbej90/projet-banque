package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	private static final String BASE_DIR = "pages/admin/";
	private static final String BASE_URL_PREFIX = "/pages/admin/";
	private static final String BASE_URL_SUFFIX = ".htm";
	
//	@RequestMapping("/private/home.html")
//	public String showHome(final HttpServletRequest request, final HttpServletResponse response) {
//		System.out.println(request.getRequestURI());
//		System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(request.getAttribute("test"));
//		System.out.println(request.getParameter("test"));
//		System.out.println(response);
//		
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		return BASE_DIR+"home";
//	}

	@RequestMapping(BASE_URL_PREFIX+"index"+BASE_URL_SUFFIX)
	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		return BASE_DIR+"index";
	}
}
