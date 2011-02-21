package com.excilys.projet.banque.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.impl.ClientServiceImpl;

@Controller
public class PrivateController {
	private static final String BASE_DIR = "pages/private/";
	private static final String BASE_URL_PREFIX = "/private/";
	private static final String BASE_URL_SUFFIX = ".htm";
	
	@RequestMapping(BASE_URL_PREFIX+"home"+BASE_URL_SUFFIX)
	public String showHome(ModelMap model) {
//	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
//		System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(request.getParameter("test"));
//		System.out.println(response);
		
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ClientService clientService = new ClientServiceImpl();
//		Client client = clientService.recupererClient(Integer.valueOf(user.getUsername()));
		Client client = new Client();
		
		model.addAttribute("client", client);
		
		return BASE_DIR+"home";
	}
}
