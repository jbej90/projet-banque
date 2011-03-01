package com.excilys.projet.banque.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {
	@RequestMapping("*")
	public ModelMap showLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();
		
		System.out.println(request);
		
		return model;
	}
}
