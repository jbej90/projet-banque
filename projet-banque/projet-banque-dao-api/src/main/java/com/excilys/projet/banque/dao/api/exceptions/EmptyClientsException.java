package com.excilys.projet.banque.dao.api.exceptions;

public class EmptyClientsException extends Exception {

	@Override
	public String getMessage() {
		return "Table client vide!";
	}
}
