package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	private static final String BASE_DIR = "admin/";
	private static final String BASE_URL_SUFFIX = ".htm";
	
	
	/**
	 * Map l'url de type /admin/index.htm
	 */
	@RequestMapping(value="index"+BASE_URL_SUFFIX, method=RequestMethod.GET)
	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		return BASE_DIR+"index";
	}
}
