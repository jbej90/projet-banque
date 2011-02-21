package com.excilys.projet.banque.dao.api.exceptions;

public class UnknownClientException extends Exception {

	private static final long serialVersionUID = 8402777533588952999L;

	@Override
	public String getMessage() {
		return "Client inconnu.";
	}

}
