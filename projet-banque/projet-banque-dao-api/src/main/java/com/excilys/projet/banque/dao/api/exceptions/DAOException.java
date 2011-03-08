package com.excilys.projet.banque.dao.api.exceptions;

@SuppressWarnings("serial")
public class DAOException extends Exception {

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}
}
