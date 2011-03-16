package com.excilys.projet.banque.web.controller;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exception.InsufficientBalanceException;
import com.excilys.projet.banque.service.api.exception.SimilarAccountsException;
import com.excilys.projet.banque.web.utils.DateUtils;
import com.excilys.projet.banque.web.utils.MessageStack;
import com.excilys.projet.banque.web.utils.ToolItem;
import com.excilys.projet.banque.web.utils.ToolbarManager;
import com.excilys.projet.banque.web.utils.WebUtils;

/**
 * Controller de la partie privée (ie: toutes url de type /private/*)
 * 
 * @author excilys
 * 
 */
@Controller
@RequestMapping("/" + WebUtils.BASE_DIR_PRIVATE)
public class PrivateController {

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
	@RequestMapping(value = "home" + WebUtils.URL_SUFFIX_PAGE, method = RequestMethod.GET)
	public String showHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:" + WebUtils.getFormatPageUri("/error/error");
		}
		// Sinon on stock les données dans le modele
		else {
			Calendar cal = Calendar.getInstance();
			List<Operation> operations;
			Map<Integer, Float> soldePrevisionnel = new HashMap<Integer, Float>();
			Set<Compte> comptes = client.getComptes();
			float total = compteService.totalComptes(comptes);

			// Récupération du total des opérations à venir pour chaque compte
			for (Compte compte : comptes) {
				operations = operationService.recupererOperationsCompte(compte.getId(), cal.getTime(), null, OperationService.ETATS_EN_COURS);
				compte.setSoldePrevisionnel(compte.getSolde() + operationService.totalOperations(operations));
			}

			model.addAttribute("client", client);
			model.addAttribute("comptes", comptes);
			model.addAttribute("soldeprevisionnel", soldePrevisionnel);
			model.addAttribute("total", total);
		}

		return WebUtils.BASE_DIR_PRIVATE + "home";
	}

	/**
	 * Map l'url de type /private/compte/{id}/operations.htm
	 */
	@RequestMapping(value = "compte/{id}" + WebUtils.URL_SUFFIX_PAGE, method = { RequestMethod.GET, RequestMethod.POST })
	public String showCompte(@PathVariable int id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;
		
		System.out.println(client);

		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = getMonthYearFilter(request);
		Calendar calNow = Calendar.getInstance();

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:" + WebUtils.getFormatPageUri("/error/error");
		}
		else {
			// Vérifie que le client est bien le propriétaire du compte
			selectedCompte = getCompteClient(id, client);

			if (selectedCompte == null) {
				MessageStack.getInstance(request).addError("Compte non valide");
				return "redirect:" + WebUtils.getFormatPageUri("/error/error");
			}
			else {
				// Récupère les opérations de ce compte
				List<Operation> operations = new LinkedList<Operation>();
				List<Operation> operationsCarte = new LinkedList<Operation>();
				float total = 0;
				float totalCarte = 0;

				operations = operationService.recupererOperationsCompteNonCarte(selectedCompte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
				operationsCarte = operationService.recupererOperationsCompteCarte(selectedCompte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
				total = operationService.totalOperations(operations);
				totalCarte = operationService.totalOperations(operationsCarte);

				model.addAttribute("compte", selectedCompte);
				model.addAttribute("operations", operations);
				model.addAttribute("operationsCarte", operationsCarte);
				model.addAttribute("operationsCarteCount", operationsCarte.size());
				model.addAttribute("soustotal", total);
				model.addAttribute("soustotalCarte", totalCarte);
				model.addAttribute("total", (total + totalCarte));
				model.addAttribute("listemois", DateFormatSymbols.getInstance(Locale.FRANCE).getMonths());
				model.addAttribute("moiscourant", cal.get(Calendar.MONTH));
				model.addAttribute("anneecourante", calNow.get(Calendar.YEAR));
				model.addAttribute("anneeselectionnee", cal.get(Calendar.YEAR));
				
				// Configuration de la toolbar
				// TODO : Refacto. Le modèle du menu ne fonctionne pas, puisqu'il nous faut des liens dynamiques :/
				ToolbarManager toolbarManager = new ToolbarManager();
				toolbarManager.addTool(new ToolItem("Exporter au format Excel", "/private/compte/"+selectedCompte.getId()+"_"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR)+WebUtils.URL_SUFFIX_XLS, "/images/export_excel.png", "Export excel", "export"));
				toolbarManager.addTool(new ToolItem("Effectuer un virement", "/private/virement/"+selectedCompte.getId()+WebUtils.URL_SUFFIX_PAGE, "/images/virement.png", "Virement", "virement"));
				model.addAttribute("toolbar", toolbarManager);
			}
		}

		return WebUtils.BASE_DIR_PRIVATE + "compte";
	}

	/**
	 * Map l'url de type /private/compte/{id}/operations/carte.htm
	 */
	@RequestMapping(value = "compte/{id}/operations/carte" + WebUtils.URL_SUFFIX_PAGE, method = { RequestMethod.GET, RequestMethod.POST })
	public String showCompteCarte(@PathVariable int id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);
		Compte selectedCompte = null;

		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = getMonthYearFilter(request);
		Calendar calNow = Calendar.getInstance();

		// Si l'instance est nulle, le client est introuvable
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			return "redirect:" + WebUtils.getFormatPageUri("/error/error");
		}
		else {
			// Récupère le compte du client et vérifie qu'il lui appartient
			selectedCompte = getCompteClient(id, client);

			if (selectedCompte == null) {
				MessageStack.getInstance(request).addError("Compte non valide");
				return "redirect:" + WebUtils.getFormatPageUri("/error/error");
			}
			else {
				List<Operation> operationsCarte = operationService.recupererOperationsCompteCarte(selectedCompte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
				float totalCarte = operationService.totalOperations(operationsCarte);

				model.addAttribute("compte", selectedCompte);
				model.addAttribute("operationscarte", operationsCarte);
				model.addAttribute("totalcarte", totalCarte);
				model.addAttribute("listemois", DateFormatSymbols.getInstance(Locale.FRANCE).getMonths());
				model.addAttribute("moiscourant", cal.get(Calendar.MONTH));
				model.addAttribute("anneecourante", calNow.get(Calendar.YEAR));
				model.addAttribute("anneeselectionnee", cal.get(Calendar.YEAR));
			}
		}

		return WebUtils.BASE_DIR_PRIVATE + "compte-carte";
	}

	/**
	 * Map l'url de type /private/virement.htm
	 */
	@RequestMapping(value = "virement" + WebUtils.URL_SUFFIX_PAGE, method = { RequestMethod.GET, RequestMethod.POST })
	public String showVirementHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Client client = getActualClient(request);

		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = getMonthYearFilter(request);
		Calendar calNow = Calendar.getInstance();

		model.addAttribute("listemois", DateFormatSymbols.getInstance(Locale.FRANCE).getMonths());
		model.addAttribute("moiscourant", cal.get(Calendar.MONTH));
		model.addAttribute("anneecourante", calNow.get(Calendar.YEAR));
		model.addAttribute("anneeselectionnee", cal.get(Calendar.YEAR));

		model.addAttribute("comptes", client.getComptes());
		model.addAttribute("idclient", client.getId());

		// Récupération des valeurs probablement stockées en session après une erreur de validation
		model.addAttribute("compte_src", request.getSession().getAttribute("compte_src"));
		model.addAttribute("compte_dest", request.getSession().getAttribute("compte_dest"));
		model.addAttribute("montant", request.getSession().getAttribute("montant"));

		// on supprime les données probablement stockées en session (pour la gestion d'erreurs)
		request.getSession().removeAttribute("compte_src");
		request.getSession().removeAttribute("compte_dest");
		request.getSession().removeAttribute("montant");

		List<Operation> virements = operationService.recupererOperationsClient(client.getId(), cal.getTime(), OperationService.TYPES_VIREMENT, OperationService.ETATS_EFFECTUE);
		List<Operation> virementsEnCours = operationService.recupererOperationsClient(client.getId(), cal.getTime(), OperationService.TYPES_VIREMENT,
				OperationService.ETATS_EN_COURS);

		model.addAttribute("virements", virements);
		model.addAttribute("virementsencours", virementsEnCours);

		return WebUtils.BASE_DIR_PRIVATE + "virement";
	}

	/**
	 * Map l'url de type /private/virement/{srcId}.htm
	 */
	@RequestMapping(value = "virement/{srcId}" + WebUtils.URL_SUFFIX_PAGE, method = RequestMethod.GET)
	public String showVirementHome(@PathVariable int srcId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String res = showVirementHome(request, response, model);

		model.addAttribute("compte_src", srcId);

		return res;
	}

	// =====================================================================================================================
	// ~ Mapping action Methods ============================================================================================
	// =====================================================================================================================

	/**
	 * Map l'url d'action de type POST /private/virement.do
	 */
	@RequestMapping(value = "virement" + WebUtils.URL_SUFFIX_FORM, method = RequestMethod.POST)
	public String doVirement(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Compte compte_src = null;
		Compte compte_dest = null;
		int compte_src_id = 0;
		int compte_dest_id = 0;
		float montant = 0;

		// Récupération des données du formulaire
		try {
			compte_src_id = Integer.parseInt(request.getParameter("compte_src"));
			compte_dest_id = Integer.parseInt(request.getParameter("compte_dest"));
			montant = Float.parseFloat(request.getParameter("montant").replace(',', '.'));
		}
		catch (NumberFormatException e) {
			MessageStack.getInstance(request).addError("Valeurs non correctes");
		}

		// Récupère le client actuel
		Client client = getActualClient(request);

		// Vérifie que les deux comptes sélectionnés soient bien différents
		if (compte_src_id == compte_dest_id) {
			MessageStack.getInstance(request).addError("Les comptes sources et destinations doivent être différents");
		}
		else {
			// Récupère les compte source et destination du client et vérifie qu'ils lui appartiennent
			compte_src = getCompteClient(compte_src_id, client);
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
		if (MessageStack.getInstance(request).getSize(MessageStack.DEFAULT_DOMAIN) == 0) {
			try {
				compteService.virer(compte_src, compte_dest, montant);
				MessageStack.getInstance(request).addInfo("Virement effectué.");

			//TODO @Damien: définir les actions associées à la réception des exceptions
			} catch (SimilarAccountsException e) {
				e.printStackTrace();
			} catch (InsufficientBalanceException e) {
				e.printStackTrace();
			}
		}

		// Si la pile d'erreur n'est pas vide, on stock les valeurs entrées pour les restituer
		request.getSession().setAttribute("compte_src", compte_src_id);
		request.getSession().setAttribute("compte_dest", compte_dest_id);
		request.getSession().setAttribute("montant", montant);

		return "redirect:/" + WebUtils.getFormatPageUri("/private/virement");
	}

	// =====================================================================================================================
	// ~ Mapping download Methods ==========================================================================================
	// =====================================================================================================================

	/**
	 * Map l'url d'action de type GET compte/{idCompte}_{month}-{year}.xls
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "compte/{idCompte}_{month}-{year}" + WebUtils.URL_SUFFIX_XLS, method = { RequestMethod.GET, RequestMethod.POST })
	public String downloadCompteExcel(@PathVariable int idCompte, @PathVariable int month, @PathVariable int year, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		// Essaye de parser la date pour le filtre d'opérations
		Calendar cal = DateUtils.getMonthYearFilter(month, year);

		// Récupère le client actuel
		Client client = getActualClient(request);
		if (client == null) {
			MessageStack.getInstance(request).addError("Client introuvable");
			response.sendRedirect(response.encodeRedirectURL("redirect:" + WebUtils.getFormatPageUri("/error/error")));
			return null;
		}

		// Récupère le compte du client
		Compte compte = client.getCompte(idCompte);
		if (compte == null) {
			MessageStack.getInstance(request).addError("Compte non valide");
			response.sendRedirect(response.encodeRedirectURL("redirect:" + WebUtils.getFormatPageUri("/error/error")));
			return null;
		}

		// Récupère les opérations de ce compte
		List<Operation> operations = new LinkedList<Operation>();
		List<Operation> operationsCarte = new LinkedList<Operation>();

		operations = operationService.recupererOperationsCompteNonCarte(compte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
		operationsCarte = operationService.recupererOperationsCompteCarte(compte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
		float total = operationService.totalOperations(operations);
		float totalCarte = operationService.totalOperations(operationsCarte);
		
		String[] mois = DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();

		model.addAttribute("mois", mois[cal.get(Calendar.MONTH)]);
		model.addAttribute("annee", cal.get(Calendar.YEAR));
		model.addAttribute("compte", compte);
		model.addAttribute("operations", operations);
		model.addAttribute("soustotal", total);
		model.addAttribute("soustotalCarte", totalCarte);
		model.addAttribute("total", (total + totalCarte));

		return "compte";
	}
	
	@RequestMapping(value = "compte/{idCompte}_{month}-{year}_color" + WebUtils.URL_SUFFIX_XLS, method = { RequestMethod.GET, RequestMethod.POST })
	public String downloadCompteExcelColor(@PathVariable int idCompte, @PathVariable int month, @PathVariable int year, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		downloadCompteExcel(idCompte, month, year, request, response, model);
		return "compteColor";
	}

	// =====================================================================================================================
	// ~ Generic Methods ===================================================================================================
	// =====================================================================================================================

	/**
	 * Récupère l'instance du client actuellement connecté. La méthode utilise l'identifiant client stocké en session.
	 * 
	 * @param request
	 *            : la requete passée aux méthodes du controlleur
	 * @return une instance du client actuel; null sinon
	 */
	private Client getActualClient(HttpServletRequest request) {
		// Récupère l'instance Client de l'utilisateur connecté
		try {
			Integer idClient = (Integer) request.getSession().getAttribute("idClient");
			return clientService.recupererClient(idClient);
		}
		catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Récupère une instance de compte à partir de son identifiant. La méthode vérifie également que ce compte appartient bien à un client donnée
	 * 
	 * @param idCompte
	 *            : identifiant du compte
	 * @param client
	 *            : instance de client devant être propriétaire du compte
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
	 * Récupère une instance de Calendar. La date configurée est celle du 1er du mois sélectionné dans le formulaire de filtrage. La méthode utilise donc les valeurs de deux champs
	 * HTML "filter_month" et "filter_year". Si aucun filtrage n'a été demandé (ie: valeurs inexistantes) ou que les données sont incorrectes, la date actuelle est retournée.
	 * 
	 * @param request
	 *            : la requete passée aux méthodes du controlleur
	 * @return une instance de Calendar correspondant à la date de filtrage; la date actuelle sinon
	 */
	private Calendar getMonthYearFilter(HttpServletRequest request) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);
		
		// Si la requete contient les parametres du formulaire no-script
		if (request.getParameterMap().containsKey("filter_month") && request.getParameterMap().containsKey("filter_year")) {
			try {
				int month = Integer.parseInt(request.getParameter("filter_month"));
				int year = Integer.parseInt(request.getParameter("filter_year"));

				cal = DateUtils.getMonthYearFilter(month, year);
			}
			catch (NumberFormatException e) {
				MessageStack.getInstance(request).addError("Format de date incorrect", "filter");
			}
		}
		// Si la requete contient le parametre du calendrier jQuery
		else if (request.getParameterMap().containsKey("datepicker")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date = sdf.parse(request.getParameter("datepicker"));
				cal.setTime(date);
				
				// Vérifie la validité de la date
				cal = DateUtils.getMonthYearFilter(cal);
			}
			catch (ParseException e) {
				MessageStack.getInstance(request).addError("Format de date incorrect", "filter");
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
