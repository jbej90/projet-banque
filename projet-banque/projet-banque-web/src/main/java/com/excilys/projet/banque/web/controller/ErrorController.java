package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.projet.banque.web.utils.MessageStack;

@Controller
@RequestMapping("/error/")
public class ErrorController {
	private static final String BASE_URL_SUFFIX = ".htm";
	
	/**
	 * Map l'url de type /admin/index.htm
	 */
	@RequestMapping(value="{code}"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showError(@PathVariable int code, final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		model.addAttribute("code", code);
		model.addAttribute("title", "Erreur "+code);
		
		switch (code) {
			case 403:
				MessageStack.getInstance(request).addError("Accès interdit");
				break;
			case 404:
				MessageStack.getInstance(request).addError("Page introuvable");
				break;
		}
		
		return "utils/error";
	}
}
