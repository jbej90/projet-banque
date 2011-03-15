package com.excilys.projet.banque.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exception.UnknownClientException;
import com.excilys.projet.banque.web.ajax.AjaxCompte;
import com.excilys.projet.banque.web.ajax.AjaxOperations;
import com.excilys.projet.banque.web.utils.WebUtils;

/**
 * Controller des requêtes AJAX de la partie privée (ie: toutes url de type /private/ajax/*)
 * 
 * @author excilys
 * 
 */
@Controller
@RequestMapping("/" + WebUtils.BASE_DIR_PRIVATE_AJAX)
public class AjaxPrivateController {

	@Autowired
	private ClientService		clientService;
	@Autowired
	private OperationService	operationService;

	// =====================================================================================================================
	// ~ Mapping Ajax Methods ==============================================================================================
	// =====================================================================================================================

	/**
	 * Map l'url de type /private/ajax/comptes.htm
	 * 
	 * @param exclude
	 *            : compte à exclure
	 * @return Liste de comptes du client courant sans celui à exclure
	 */
	@Secured({ "ROLE_USER" })
	@RequestMapping(value = "comptes" + WebUtils.URL_SUFFIX_PAGE, method = RequestMethod.GET)
	public @ResponseBody
	void getComptes(@RequestParam(value = "exclude", required = false, defaultValue = "-1") int exclude, HttpServletRequest request,
			HttpServletResponse response) {
		Set<AjaxCompte> listComptes = new TreeSet<AjaxCompte>();
		Client client = getActualClient(request);

		if (client != null) {
			// Récupération de la liste des comptes du client excluant celui indiqué
			Set<Compte> comptes = client.getComptes();
			for (Compte c : comptes) {
				if (c.getId() != exclude) {
					listComptes.add(new AjaxCompte(c.getId(), c.getLibelle(), c.getSolde()));
				}
			}
		}

		// Conversion du résultat en JSON
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
		MediaType jsonMimeType = MediaType.APPLICATION_JSON;
		if (jsonConverter.canWrite(listComptes.getClass(), jsonMimeType)) {
			try {
				jsonConverter.write(listComptes, jsonMimeType, new ServletServerHttpResponse(response));
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			catch (HttpMessageNotWritableException e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Map l'url de type /private/ajax/compte/${compte}.htm
	 * 
	 * @param exclude
	 *            : compte à exclure
	 * @return Liste des opérations d'un des comptes du client courant
	 */
	@Secured({ "ROLE_USER" })
	@RequestMapping(value = "compte/{compte}" + WebUtils.URL_SUFFIX_PAGE, method = RequestMethod.GET)
	public @ResponseBody
	void getOperationsComptes(@PathVariable("compte") int idCompte,
			@RequestParam(value = "year", required = false, defaultValue = "-1") int year, @RequestParam(value = "month", required = false, defaultValue = "-1") int month,
			HttpServletRequest request, HttpServletResponse response) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		Compte compte = null;
		Client client = getActualClient(request);
		if (client != null) {
			compte = client.getCompte(idCompte);
		}

		// Si les deux variables sont non nulles, les données sont valides (ie: compte existant et appartenant au client)
		if (client != null && compte != null) {
			// configuration des types d'opération à exclure de la recherche
			List<Type> typesCarte = new LinkedList<Type>();
			typesCarte.add(Type.OP_CARTE_DIFF);
			typesCarte.add(Type.OP_CARTE_IMM);

			// Récupération du mois à afficher (avec ou sans filtrage)
			cal = getMonthYearFilter(month, year);

			// Récupération des opérations du mois sélectionné
			List<Operation> ops;
			try {
				ops = operationService.recupererOperationsCompteNonCarte(compte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);
				List<Operation> opsCarte = operationService.recupererOperationsCompteCarte(compte.getId(), cal.getTime(), OperationService.ETATS_EFFECTUE);

				float sousTotal = operationService.totalOperations(ops);
				float sousTotalCarte = operationService.totalOperations(opsCarte);

				AjaxOperations operations = new AjaxOperations(ops, sousTotalCarte, sousTotal);


				// Conversion du résultat en JSON
				MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
				MediaType jsonMimeType = MediaType.APPLICATION_JSON;
				if (jsonConverter.canWrite(operations.getClass(), jsonMimeType)) {
						jsonConverter.write(operations, jsonMimeType, new ServletServerHttpResponse(response));
				}
				
			} catch (HttpMessageNotWritableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
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
		Client client = null;
		try {
			Integer idClient = (Integer) request.getSession().getAttribute("idClient");
			client = clientService.recupererClient(idClient);
		}
		//TODO @Damien: définir les actions associées à la réception des exceptions
		catch (NullPointerException e) {
			e.printStackTrace();
		} catch (UnknownClientException e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * Récupère une instance de Calendar. La date configurée est celle du 1er du mois sélectionné dans le formulaire de filtrage. La méthode utilise donc les valeurs month et year.
	 * Si aucun filtrage n'a été demandé (ie: valeurs inexistantes) ou que les données sont incorrectes, la date actuelle est retournée.
	 * 
	 * @param request
	 *            : utilisée pour le possible message d'erreur
	 * @param month
	 *            : mois à utiliser pour le filtrage
	 * @param year
	 *            : année à utiliser pour le filtrage
	 * @return une instance de Calendar correspondant à la date de filtrage; la date actuelle sinon
	 */
	private Calendar getMonthYearFilter(int month, int year) {
		Calendar cal = Calendar.getInstance(Locale.FRANCE);

		if (month >= 0 && month < 12 && year >= cal.get(Calendar.YEAR) - 3 && year <= cal.get(Calendar.YEAR)) {
			try {
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.YEAR, year);
			}
			catch (NumberFormatException e) {
			}
		}

		return cal;
	}
}
