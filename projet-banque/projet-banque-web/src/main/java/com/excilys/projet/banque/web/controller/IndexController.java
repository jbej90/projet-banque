package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * Map l'url de type index.html et redirige l'utilisateur en fonction de ses droits
	 */
	@RequestMapping("index.htm")
	public String redirectByrole(HttpSession session, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/index.htm";
		}
		else if (request.isUserInRole("ROLE_USER")) {
			return "redirect:/private/home.htm";
		}

		return "redirect:/login.htm";
	}
}