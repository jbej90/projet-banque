package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
	private static final String BASE_DIR = "";
	private static final String BASE_URL_SUFFIX = ".htm";

	@RequestMapping("login"+BASE_URL_SUFFIX)
	public String showLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("content", "/WEB-INF/pages/login.jsp");
		
		return BASE_DIR+"login";
	}
}