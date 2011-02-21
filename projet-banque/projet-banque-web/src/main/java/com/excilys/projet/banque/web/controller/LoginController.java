package com.excilys.projet.banque.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	private static final String BASE_DIR = "pages/";

	@RequestMapping("/login.html")
	public String showLogin() {
		return BASE_DIR+"login";
	}
}