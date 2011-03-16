package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.projet.banque.web.utils.WebUtils;

/**
 * Controller de la page de login.
 * 
 * @author excilys
 *
 */
@Controller
@RequestMapping("/" + WebUtils.BASE_DIR_HOME)
public class LoginController {

	/**
	 * Map l'url de type /login.htm
	 */
	@RequestMapping("login" + WebUtils.URL_SUFFIX_PAGE)
	public String showLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return WebUtils.BASE_DIR_HOME + "login";
	}
}