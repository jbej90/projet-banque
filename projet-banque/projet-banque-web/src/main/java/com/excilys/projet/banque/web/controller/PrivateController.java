package com.excilys.projet.banque.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Controller
@RequestMapping("/private/")
public class PrivateController {
	private static final String BASE_DIR = "private/";
	private static final String BASE_URL_SUFFIX = ".htm";
	
	@Resource
	private ClientService clientService;

	
	/**
	 * Map l'url de type /private/home.htm
	 */
	@RequestMapping(value="home"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showHome(ModelMap model) {
//	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
//		System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
//		System.out.println(request.getParameter("test"));
//		System.out.println(response);
		
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Client client = null;
		try {
			client = clientService.recupererClient(user.getUsername());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("client", client);
		model.addAttribute("comptes", client.getComptes());
		
		return BASE_DIR+"home";
	}
	

	/**
	 * Map l'url de type /private/compte-{id}.htm
	 */
	@RequestMapping(value="compte-{id}"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showCompte(@PathVariable long id, ModelMap model) {

		model.addAttribute("compte_id", id);
		
		return BASE_DIR+"compte";
	}
	

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	
	
	/**
	 * Import de données de références
	 * TODO : Importer les bonnes données 
	 */
	@ModelAttribute("types")
	public Collection<String> getTypes() {
		List<String> list = new ArrayList<String>();
		list.add("virement");
		list.add("dépot");
		list.add("retrait");
		// ...
		return list;
	}
}
