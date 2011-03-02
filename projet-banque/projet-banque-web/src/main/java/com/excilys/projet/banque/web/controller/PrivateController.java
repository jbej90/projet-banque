package com.excilys.projet.banque.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import com.excilys.projet.banque.web.utils.Menu;
import com.excilys.projet.banque.web.utils.MenuItem;
import com.excilys.projet.banque.web.utils.MessageStack;

@Controller
@RequestMapping("/private/")
public class PrivateController {

	private static final String	BASE_DIR		= "private/";
	private static final String	BASE_URL_SUFFIX	= ".htm";

	// ~ Attributes =======================================================================================================

	@Autowired
	private ClientService		clientService;
	@Autowired
	private CompteService		compteService;
	@Autowired
	private OperationService	operationService;

	// ~ Mapping show Methods ==============================================================================================

	/**
	 * Map l'url de type /private/home.htm
	 */
	@RequestMapping(value = "home" + BASE_URL_SUFFIX)
	public String showHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");

		}
		// Sinon on stock les données dans le modele
		else {
			Set<Compte> comptes = null;
			float total = 0;
			try {
				comptes = client.getComptes();
				total = compteService.totalComptes(comptes);
			}
			catch (ServiceException e) {
				e.printStackTrace();
			}

			model.addAttribute("client", client);
			model.addAttribute("comptes", client.getComptes());
			model.addAttribute("total", total);

			// TODO : Refactorer la gestion des menus
			int menuid = 0;
			Menu menu = new Menu();
			menu.addItem(new MenuItem(menuid++, "Mon résumé", "private/home.htm"));
			menu.addItem(new MenuItem(menuid++, "Mes virements", "private/virement.htm"));
			menu.setItemSelected(0);
			model.addAttribute("menu", menu);
			// TODO EOF : Refactorer la gestion des menus
		}

		return BASE_DIR + "home";
	}

	/**
	 * Map l'url de type /private/compte-{id}.htm
	 */
	@RequestMapping(value = "compte-{id}" + BASE_URL_SUFFIX)
	public String showCompte(@PathVariable long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;

		// Essaye de parser la date pour le filtre d'opérations
		if (request.getParameterMap().containsKey("filter_date")) {
			try {
				date = sdf.parse(request.getParameter("filter_month"));
			} catch (ParseException e) {
				MessageStack.getInstance(request).addError("Format de date incorrect");
			}
		}

		// Si la date est nulle (parsing incorrect ou valeur par défaut), on prend la date par défaut
		if (date == null) {
			date = new Date();
		}
		
		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:/error/error.htm";
		}
		else {
			// TODO: A optimiser
			// Vérifie que le client est bien le propriétaioperation0_.date_op as date2_4re du compte
			Set<Compte> comptes = client.getComptes();
			for (Compte compte : comptes) {
				if (compte.getId() == id) {
					selectedCompte = compte;
					break;
				}
			}
			
			if (selectedCompte == null) {
				MessageStack.getInstance(request).addError("Compte non valide");
				return "redirect:/error/error.htm";
			} else {
				// Récupère les opérations de ce compte
				List<Operation> operations 		= new LinkedList<Operation>();
				List<Operation> operationsCarte = new LinkedList<Operation>();
				float total 		= 0;
				float totalCarte 	= 0;
				try {
					List<Type> listTypeCarte = new LinkedList<Type>();
					listTypeCarte.add(Type.OP_CARTE_DIFF);
					listTypeCarte.add(Type.OP_CARTE_IMM);
					
					operations 		= operationService.recupererOperationsSansType(selectedCompte, date, listTypeCarte);
					operationsCarte = operationService.recupererOperations(selectedCompte, date, listTypeCarte);
					total 			= operationService.totalOperations(operations);
					totalCarte 		= operationService.totalOperations(operationsCarte);
				}
				catch (ServiceException e) {
					e.printStackTrace();
				}
	
				model.addAttribute("compte", selectedCompte);
				model.addAttribute("operations", operations);
				model.addAttribute("operationsCarte", operationsCarte);
				model.addAttribute("operationsCarteCount", operationsCarte.size());
				model.addAttribute("soustotal", total);
				model.addAttribute("soustotalCarte", totalCarte);
				model.addAttribute("total", total+totalCarte);
			}
		}

		// TODO : Refactorer la gestion des menus
		int menuid = 0;
		Menu menu = new Menu();
		menu.addItem(new MenuItem(menuid++, "Mon résumé", "private/home.htm"));
		menu.addItem(new MenuItem(menuid++, "Mes virements", "private/virement-"+selectedCompte.getId()+".htm"));
		menu.setItemSelected(0);
		model.addAttribute("menu", menu);
		// TODO EOF : Refactorer la gestion des menus

		return BASE_DIR + "compte";
	}

	/**
	 * Map l'url de type /private/compte-carte-{id}.htm
	 */
	@RequestMapping(value = "compte-carte-{id}" + BASE_URL_SUFFIX)
	public String showCompteCarte(@PathVariable long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;

		// Essaye de parser la date pour le filtre d'opérations
		if (request.getParameterMap().containsKey("filter_date")) {
			try {
				date = sdf.parse(request.getParameter("filter_month"));
			} catch (ParseException e) {
				MessageStack.getInstance(request).addError("Format de date incorrect");
			}
		}

		// Si la date est nulle (parsing incorrect ou valeur par défaut), on prend la date par défaut
		if (date == null) {
			date = new Date();
		}
		
		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:/error/error.htm";
		}
		else {
			// TODO: A optimiser
			// Vérifie que le client est bien le propriétaire du compte
			Set<Compte> comptes = client.getComptes();
			for (Compte compte : comptes) {
				if (compte.getId() == id) {
					selectedCompte = compte;
					break;
				}
			}
			
			if (selectedCompte == null) {
				MessageStack.getInstance(request).addError("Compte non valide");
				return "redirect:/error/error.htm";
			} else {
				// Récupère les opérations de ce compte
				List<Operation> operationsCarte = null;
				float totalCarte = 0;
				try {
					List<Type> listTypeCarte = new LinkedList<Type>();
					listTypeCarte.add(Type.OP_CARTE_DIFF);
					listTypeCarte.add(Type.OP_CARTE_IMM);
					
					operationsCarte = operationService.recupererOperations(selectedCompte, date, listTypeCarte);
					totalCarte 		= operationService.totalOperations(operationsCarte);
				}
				catch (ServiceException e) {
					e.printStackTrace();
				}
	
				model.addAttribute("compte", selectedCompte);
				model.addAttribute("operationsCarte", operationsCarte);
				model.addAttribute("totalCarte", totalCarte);
			}
		}

		// TODO : Refactorer la gestion des menus
		int menuid = 0;
		Menu menu = new Menu();
		menu.addItem(new MenuItem(menuid++, "Mon résumé", "private/home.htm"));
		menu.addItem(new MenuItem(menuid++, "Mes virements", "private/virement-"+selectedCompte.getId()+".htm"));
		menu.setItemSelected(0);
		model.addAttribute("menu", menu);
		// TODO EOF : Refactorer la gestion des menus

		return BASE_DIR + "compte-carte";
	}

	/**
	 * Map l'url de type /private/virement.htm
	 */
	@RequestMapping(value = "virement" + BASE_URL_SUFFIX)
	public String showVirementHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		model.addAttribute("comptes", client.getComptes());

		// TODO : A implémenter
		// model.addAttribute("virements", );

		// TODO : Refactorer la gestion des menus
		int menuid = 0;
		Menu menu = new Menu();
		menu.addItem(new MenuItem(menuid++, "Mon résumé", "private/home.htm"));
		menu.addItem(new MenuItem(menuid++, "Mes virements", "private/virement.htm"));
		menu.setItemSelected(1);
		model.addAttribute("menu", menu);
		// TODO EOF : Refactorer la gestion des menus

		return BASE_DIR + "virement";
	}

	/**
	 * Map l'url de type /private/virement-{srcId}.htm
	 */
	@RequestMapping(value = "virement-{srcId}" + BASE_URL_SUFFIX)
	public String showVirementHome(@PathVariable int srcId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("compte_src", srcId);
		
		return showVirementHome(request, response, model);
	}

	// ~ Mapping action Methods =============================================================================================

	/**
	 * Map l'url d'action de type POST /private/virement.do
	 */
	@RequestMapping(value = "virement.do")
	public String doVirement(HttpServletRequest request, HttpServletResponse response, RequestMethod method, ModelMap model) {
		// Opération de controle sur le type de requete demandé
//		if (method != RequestMethod.POST) {
//			return "redirect:/error/405.htm";
//		}
		
		int compte_src_id = 0;
		int compte_dest_id = 0;
		float montant = 0;
		try {
			compte_src_id = Integer.parseInt(request.getParameter("compte_src"));
			compte_dest_id = Integer.parseInt(request.getParameter("compte_dest"));
			montant = Float.parseFloat(request.getParameter("montant"));
		}
		catch (NumberFormatException e) {
			MessageStack.getInstance(request).addError("Valeurs non correctes");
		}

		Client client = getActualClient(request);
		Compte compte_src = null;
		Compte compte_dest = null;

		// Vérifie que les deux comptes sélectionnés soient bien différents
		if (compte_src_id == compte_dest_id) {
			MessageStack.getInstance(request).addError("Les comptes sources et destinations doivent être différents");
		}
		else {
			// Récupère les instances des deux comptes
			Set<Compte> comptes = client.getComptes();
			for (Compte compte : comptes) {
				if (compte.getId() == compte_src_id) {
					compte_src = compte;
				}
				else if (compte.getId() == compte_dest_id) {
					compte_dest = compte;
				}
				// Si les deux comptes ont été trouvés, on sort de la boucle
				if (compte_src != null && compte_dest != null) {
					break;
				}
			}

			// Vérifie que le client est bien le propriétaire des comptes
			if (compte_src == null) {
				MessageStack.getInstance(request).addError("Le compte source n'est pas valide");
			}
			if (compte_dest == null) {
				MessageStack.getInstance(request).addError("Le compte destination n'est pas valide");
			}
		}

		// Vérifie que le montant soit correct
		if (montant <= 0) {
			MessageStack.getInstance(request).addError("Le montant doit être positif non null");
		}

		// Si la pile d'erreur est vide, tout s'est bien passé
		// On valide le traitement
		if (MessageStack.getInstance(request).getSize() == 0) {
			if (compteService.virer(compte_src, compte_dest, montant)) {
				MessageStack.getInstance(request).addInfo("Virement enregistré. Il sera traité cette nuit");
			}
			else {
				MessageStack.getInstance(request).addError("L'enregistrement du virement a échoué");
			}
		}
		return "redirect:/private/virement" + BASE_URL_SUFFIX;
	}

	// ~ Generic Methods ==================================================================================================

	private Client getActualClient(HttpServletRequest request) {
		// Réupère l'instance Client de l'utilisateur connecté
		Client client = null;
		try {
			Integer idClient = (Integer) request.getSession().getAttribute("idClient");
			client = clientService.recupererClient(idClient);
		}
		catch (ServiceException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * Import de données de références TODO : Importer les bonnes données
	 */
	// @ModelAttribute("types")
	// public Collection<String> getTypes() {
	// List<String> list = new ArrayList<String>();
	// list.add("virement");
	// list.add("dépot");
	// list.add("retrait");
	// // ...
	// return list;
	// }

	// ~ Accessors ========================================================================================================

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public void setCompteService(CompteService compteService) {
		this.compteService = compteService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}
}
