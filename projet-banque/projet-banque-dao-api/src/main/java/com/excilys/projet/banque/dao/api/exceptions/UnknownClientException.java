package com.excilys.projet.banque.dao.api.exceptions;

public class UnknownClientException extends Exception {

	@Override
	public String getMessage() {
		return "Client inconnu.";
	}

}
