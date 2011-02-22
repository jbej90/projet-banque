package com.excilys.projet.banque.dao.api.exceptions;

@SuppressWarnings("serial")
public class UnknownCompteException extends Exception {

	@Override
	public String getMessage() {
		return "Compte inconnu.";
	}
}
