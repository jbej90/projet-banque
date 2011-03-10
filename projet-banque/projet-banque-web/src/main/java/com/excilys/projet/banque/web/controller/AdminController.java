package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.projet.banque.web.utils.WebUtils;

/**
 * Controller de la partie admin (ie: toutes url de type /admin/*)
 * 
 * @author excilys
 * 
 */
@Controller
@RequestMapping("/" + WebUtils.BASE_DIR_ADMIN)
public class AdminController {

	/**
	 * Map l'url de type /admin/index.htm
	 */
	@RequestMapping(value = "index" + WebUtils.URL_SUFFIX_PAGE, method = RequestMethod.GET)
	public String showHome(final HttpServletRequest request, final HttpServletResponse response, ModelMap model) {
		return WebUtils.BASE_DIR_ADMIN + "index";
	}
}
