package com.excilys.projet.banque.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import com.excilys.projet.banque.web.utils.Menu;
import com.excilys.projet.banque.web.utils.MenuItem;
import com.excilys.projet.banque.web.utils.MessageStack;

@Controller
@RequestMapping("/private/")
public class PrivateController {
	private static final String BASE_DIR = "private/";
	private static final String BASE_URL_SUFFIX = ".htm";

	@Resource
	private ClientService clientService;
	@Resource
	private CompteService compteService;
//	@Resource
//	private OperationService operationService;


	/**
	 * Map l'url de type /private/home.htm
	 */
	@RequestMapping(value="home"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
		// Sinon on stock les données dans le modele
		} else {
			model.addAttribute("client", client);
			model.addAttribute("comptes", client.getComptes());

			int id = 0;
			Menu menu = new Menu();
			menu.addItem(new MenuItem(id++, "item 1", "private/home.htm"));
			for (Compte compte : client.getComptes()) {
				menu.addItem(new MenuItem(id++, "compte n°"+compte.getId(), "private/compte-"+compte.getId()+".htm"));	
			}
			menu.setItemSelected(1);
			model.addAttribute("menu", menu);
		}


		return BASE_DIR+"home";
	}


	/**
	 * Map l'url de type /private/compte-{id}.htm
	 */
	@RequestMapping(value="compte-{id}"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showCompte(@PathVariable long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
		// Sinon on stock les données dans le modele
		} else {
			// Vérifie que le client est bien le propriétaire du compte
			Set<Compte> comptes = client.getComptes();
			Compte selectedCompte = null;
			for (Compte compte : comptes) {
				if (compte.getId() == id) {
					selectedCompte = compte;
					break;
				}
			}
			
			if (selectedCompte == null) {
				MessageStack.getInstance(request).addError("Ce compte ne vous appartient pas");
			}
			
//			// Récupère les opérations de ce compte
//			List<Operation> operations = null;
//			try {
//				operations = operationService.recupererOperations(selectedCompte);
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}
//			
//			model.addAttribute("compte", selectedCompte);
//			model.addAttribute("operations", operations);
		}

		return BASE_DIR+"compte";
	}


	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	
	public void setCompteService(CompteService compteService) {
		this.compteService = compteService;
	}

//	public void setOperationService(OperationService operationService) {
//		this.operationService = operationService;
//	}


	private Client getActualClient(HttpServletRequest request) {
		// Réupère l'instance Client de l'utilisateur connecté
		Client client = null;
		try {
			Integer idClient = (Integer)request.getSession().getAttribute("idClient");
			client = clientService.recupererClient(idClient);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return client;
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
