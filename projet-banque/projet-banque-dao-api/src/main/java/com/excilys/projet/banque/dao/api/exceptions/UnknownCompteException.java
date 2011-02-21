package com.excilys.projet.banque.dao.api.exceptions;

public class UnknownCompteException extends Exception {

	private static final long serialVersionUID = 3280912954772819299L;

	@Override
	public String getMessage() {
		return "Compte inconnu.";
	}
}
