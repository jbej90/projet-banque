package com.excilys.projet.banque.web.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import com.excilys.projet.banque.web.utils.MessageStack;

@Controller
@RequestMapping("/private/")
public class PrivateController {

	private static final String	BASE_DIR		= "private/";
	private static final String	BASE_URL_SUFFIX	= ".htm";

	@Autowired
	private ClientService		clientService;
	@Autowired
	private CompteService		compteService;
	@Autowired
	private OperationService	operationService;

	// =====================================================================================================================
	// ~ Mapping show Methods ==============================================================================================
	// =====================================================================================================================

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
		}

		return BASE_DIR + "home";
	}

	/**
	 * Map l'url de type /private/compte/{id}/operations.htm
	 */
	@RequestMapping(value = "compte/{id}" + BASE_URL_SUFFIX)
	public String showCompte(@PathVariable int id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;

		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = getMonthYearFilter(request);
		Calendar calNow = Calendar.getInstance();
		
		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:/error/error.htm";
		}
		else {
			// Vérifie que le client est bien le propriétaire du compte
			selectedCompte = getCompteClient(id, client);
			
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
					
					operations 		= operationService.recupererOperationsSansType(selectedCompte, cal.getTime(), listTypeCarte);
					operationsCarte = operationService.recupererOperations(selectedCompte, cal.getTime(), listTypeCarte);
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
				model.addAttribute("listemois", DateFormatSymbols.getInstance(Locale.FRANCE).getMonths());
				model.addAttribute("moiscourant", cal.get(Calendar.MONTH));
				model.addAttribute("anneecourante", calNow.get(Calendar.YEAR));
				model.addAttribute("anneeselectionnee", cal.get(Calendar.YEAR));
			}
		}
		
		return BASE_DIR + "compte";
	}

	/**
	 * Map l'url de type /private/compte/{id}/operations/carte.htm
	 */
	@RequestMapping(value = "compte/{id}/operations/carte" + BASE_URL_SUFFIX)
	public String showCompteCarte(@PathVariable int id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;
		
		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = getMonthYearFilter(request);
		Calendar calNow = Calendar.getInstance();

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:/error/error.htm";
		}
		else {
			// Récupère le compte du client et vérifie qu'il lui appartient
			selectedCompte = getCompteClient(id, client);
			
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
					
					operationsCarte = operationService.recupererOperations(selectedCompte, cal.getTime(), listTypeCarte);
					totalCarte 		= operationService.totalOperations(operationsCarte);
				}
				catch (ServiceException e) {
					e.printStackTrace();
				}
				
				model.addAttribute("compte", selectedCompte);
				model.addAttribute("operationscarte", operationsCarte);
				model.addAttribute("totalcarte", totalCarte);
				model.addAttribute("listemois", DateFormatSymbols.getInstance(Locale.FRANCE).getMonths());
				model.addAttribute("moiscourant", cal.get(Calendar.MONTH));
				model.addAttribute("anneecourante", calNow.get(Calendar.YEAR));
				model.addAttribute("annee50.0selectionnee", cal.get(Calendar.YEAR));
			}
		}

		return BASE_DIR + "compte-carte";
	}

	/**
	 * Map l'url de type /private/virement.htm
	 */
	@RequestMapping(value = "virement" + BASE_URL_SUFFIX)
	public String showVirementHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		model.addAttribute("comptes", client.getComptes());
		
		// Récupération des valeurs probablement stockées en session après une erreur de validation
		model.addAttribute("compte_src", request.getSession().getAttribute("compte_src"));
		model.addAttribute("compte_dest", request.getSession().getAttribute("compte_dest"));
		model.addAttribute("montant", request.getSession().getAttribute("montant"));

		// TODO : A implémenter
		// model.addAttribute("virements", );

		return BASE_DIR + "virement";
	}

	/**
	 * Map l'url de type /private/virement/{srcId}.htm
	 */
	@RequestMapping(value = "virement/{srcId}" + BASE_URL_SUFFIX)
	public String showVirementHome(@PathVariable int srcId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("compte_src", srcId);
		
		return showVirementHome(request, response, model);
	}

	// =====================================================================================================================
	// ~ Mapping action Methods ============================================================================================
	// =====================================================================================================================

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
			// Récupère les compte source et destination du client et vérifie qu'ils lui appartiennent
			compte_src 	= getCompteClient(compte_src_id, client);
			compte_dest = getCompteClient(compte_dest_id, client);

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
			montant = 0;
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
			
			// on supprime les données probablement stockées en session (pour la gestion d'erreurs)
			request.getSession().removeAttribute("compte_src");
			request.getSession().removeAttribute("compte_dest");
			request.getSession().removeAttribute("montant");
		} else {
			// Si la pile d'erreur n'est pas vide, on stock les valeurs entrées pour les restituer
			request.getSession().setAttribute("compte_src", compte_src_id);
			request.getSession().setAttribute("compte_dest", compte_dest_id);
			request.getSession().setAttribute("montant", montant);
		}
		return "redirect:/private/virement" + BASE_URL_SUFFIX;
	}

	// =====================================================================================================================
	// ~ Generic Methods ===================================================================================================
	// =====================================================================================================================

	/**
	 * Récupère l'instance du client actuellement connecté.
	 * La méthode utilise l'identifiant client stocké en session.
	 * 
	 * @param request : la requete passée aux méthodes du controlleur
	 * @return une instance du client actuel; null sinon
	 */
	private Client getActualClient(HttpServletRequest request) {
		// Récupère l'instance Client de l'utilisateur connecté
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
	 * Récupère une instance de compte à partir de son identifiant.
	 * La méthode vérifie également que ce compte appartient bien à un client donnée
	 *  
	 * @param idCompte : identifiant du compte
	 * @param client : instance de client devant être propriétaire du compte
	 * @return instance de Compte correspondant à l'id si le client est le propriétaire; null sinon
	 */
	private Compte getCompteClient(int idCompte, Client client) {
		// Vérifie que le client est bien le propriétaire du compte
		Set<Compte> comptes = client.getComptes();
		for (Compte compte : comptes) {
			if (compte.getId() == idCompte) {
				return compte;
			}
		}
		return null;
	}
	
	/**
	 * Récupère une instance de Calendar. La date configurée est celle du 1er du mois sélectionné dans le formulaire de filtrage.
	 * La méthode utilise donc les valeurs de deux champs HTML "filter_month" et "filter_year".
	 * Si aucun filtrage n'a été demandé (ie: valeurs inexistantes) ou que les données sont incorrectes, la date actuelle est retournée.
	 * 
	 * @param request : la requete passée aux méthodes du controlleur
	 * @return une instance de Calendar correspondant à la date de filtrage; la date actuelle sinon
	 */
	private Calendar getMonthYearFilter(HttpServletRequest request) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		
		if (request.getParameterMap().containsKey("filter_month") && request.getParameterMap().containsKey("filter_year")) {
			try {
				int month = Integer.parseInt(request.getParameter("filter_month"));
				int year = Integer.parseInt(request.getParameter("filter_year"));
				
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
			} catch (NumberFormatException e) {
				MessageStack.getInstance(request).addError("Format de date incorrect");
			}
		}
		
		return cal;
	}

	// =====================================================================================================================
	// ~ Accessors =========================================================================================================
	// =====================================================================================================================
	
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
