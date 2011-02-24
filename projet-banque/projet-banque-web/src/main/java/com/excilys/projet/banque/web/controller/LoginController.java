package com.excilys.projet.banque.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
	private static final String BASE_DIR = "";
	private static final String BASE_URL_SUFFIX = ".htm";

	@RequestMapping("login"+BASE_URL_SUFFIX)
	public String showLogin() {
		return BASE_DIR+"login";
	}
}