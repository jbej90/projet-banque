package com.excilys.projet.banque.web.controller;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import com.excilys.projet.banque.web.ajax.AjaxCompte;

/**
 * Controller des requêtes AJAX de la partie privée (ie: toutes url de type /private/ajax/*)
 * 
 * @author excilys
 *
 */
@Controller
@RequestMapping("/private/ajax/")
public class AjaxPrivateController {
	/** Suffix des URI à mapper */
	private static final String	BASE_URL_SUFFIX	= ".htm";

	@Autowired
	private ClientService		clientService;

	/**
	 * Map l'url de type /private/comptes/{client}.htm
	 * 
	 * @param client: client courant
	 * @param exclude: compte à exclure
	 * @return Liste de comptes sans celui à exclure
	 */
	@RequestMapping(value = "comptes/{client}" + BASE_URL_SUFFIX, method = RequestMethod.GET)
	public @ResponseBody void getComptes(@PathVariable int client, @RequestParam(required=false, defaultValue="-1") int exclude, HttpServletResponse response) {
		Client cli = null;
		try {
			cli = clientService.recupererClient(client);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		// Récupération de la liste des clients
		Set<Compte> comptes = cli.getComptes();
		Set<AjaxCompte> listComptes = new TreeSet<AjaxCompte>();
		for (Compte c : comptes) {
			if (c.getId() != exclude) {
				listComptes.add(new AjaxCompte(c.getId(), c.getLibelle(), c.getSolde()));
			}
		}
		
		// Conversion du résultat en JSON
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
	    MediaType jsonMimeType = MediaType.APPLICATION_JSON;
	    if (jsonConverter.canWrite(listComptes.getClass(), jsonMimeType)) {
	        try {
	            jsonConverter.write(listComptes, jsonMimeType, new ServletServerHttpResponse(response));
	        } catch (IOException e1) {
	        	e1.printStackTrace();
	        } catch (HttpMessageNotWritableException e2) {
	        	e2.printStackTrace();
	        }
	    }
	}
}
