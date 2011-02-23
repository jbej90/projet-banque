package com.excilys.projet.banque.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Controller
public class PrivateController {
	private static final String BASE_DIR = "private/";
	private static final String BASE_URL_PREFIX = "/private/";
	private static final String BASE_URL_SUFFIX = ".htm";
	
	@Resource
	private ClientService clientService;

	@RequestMapping(BASE_URL_PREFIX+"home"+BASE_URL_SUFFIX)
	public String showHome(ModelMap model) {
//	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
//		System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(request.getParameter("test"));
//		System.out.println(response);
		
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		Client client = null;
		try {
			client = clientService.recupererClient(Integer.valueOf(user.getUsername()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("client", client);
		
		List<Compte> comptes = new ArrayList<Compte>(client.getComptes());
		Collections.sort(comptes);
		model.addAttribute("comptes", comptes);
		
		return BASE_DIR+"home";
	}
	

	@RequestMapping(BASE_URL_PREFIX+"compte-*"+BASE_URL_SUFFIX)
	public String showCompte(ModelMap model) {
		return BASE_DIR+"compte";
	}
	

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
}
