package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.projet.banque.web.utils.WebUtils;

@Controller
public class IndexController {

	/**
	 * Map l'url de type index.html et redirige l'utilisateur en fonction de ses droits
	 */
	@RequestMapping("index" + WebUtils.URL_SUFFIX_PAGE)
	public String redirectByrole(HttpSession session, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:"+WebUtils.getFormatPageUri("/admin/index");
		}
		else if (request.isUserInRole("ROLE_USER")) {
			return "redirect:"+WebUtils.getFormatPageUri("/private/home");
		}

		return "redirect:"+WebUtils.getFormatPageUri("/login");
	}
}