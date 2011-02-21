package com.excilys.projet.banque.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PrivateController {
	private static final String BASE_DIR = "pages/private/";
	
	@RequestMapping("/private/home.html")
	public String showHome(ModelMap model) {
//	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
//		System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(request.getParameter("test"));
//		System.out.println(response);
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		
		return BASE_DIR+"home";
	}
}
