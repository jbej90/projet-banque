package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.projet.banque.web.utils.MessageStack;

@Controller
@RequestMapping("/error/")
public class ErrorController {
	private static final String BASE_URL_SUFFIX = ".htm";
	
	/**
	 * Map l'url de type /error/{code}.htm
	 */
	@RequestMapping(value="{code}"+BASE_URL_SUFFIX)
	public String showError(@PathVariable int code, final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		model.addAttribute("title", "Erreur "+code);
		
		switch (code) {
			case 400:
				MessageStack.getInstance(request).addError("Syntaxe de requête erroné");
				break;
			case 403:
				MessageStack.getInstance(request).addError("Accès interdit");
				break;
			case 404:
				MessageStack.getInstance(request).addError("Page introuvable");
				break;
			case 405:
				MessageStack.getInstance(request).addError("Méthode de requête non autorisé");
				break;
			case 500:
				MessageStack.getInstance(request).addError("Erreur interne");
				break;
			default:
				model.addAttribute("title", "Erreur inconnue");
				MessageStack.getInstance(request).addError("Erreur inconnue");
				break;
		}
		
		return "utils/error";
	}

	/**
	 * Map l'url de type /error/error.htm
	 */
	@RequestMapping(value="error"+BASE_URL_SUFFIX)
	public String showError(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		model.addAttribute("title", "Erreur");
		
		return "utils/error";
	}
}
