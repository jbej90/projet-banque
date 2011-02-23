package com.excilys.projet.banque.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	private static final String BASE_DIR = "";
	private static final String BASE_URL_PREFIX = "/";
	private static final String BASE_URL_SUFFIX = ".htm";

	@RequestMapping(BASE_URL_PREFIX+"login"+BASE_URL_SUFFIX)
	public String showLogin() {
		System.out.println("coucou");
		return BASE_DIR+"login";
		
	}
}