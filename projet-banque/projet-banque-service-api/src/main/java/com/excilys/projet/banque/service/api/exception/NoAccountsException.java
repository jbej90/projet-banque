package com.excilys.projet.banque.service.api.exception;

public class NoAccountsException extends Exception {

	public NoAccountsException() {
		super();
	}

	public NoAccountsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccountsException(String message) {
		super(message);
	}

	public NoAccountsException(Throwable cause) {
		super(cause);
	}

}
