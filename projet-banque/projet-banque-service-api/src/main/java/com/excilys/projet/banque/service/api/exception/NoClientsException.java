package com.excilys.projet.banque.service.api.exception;

public class NoClientsException extends Exception {

	public NoClientsException() {
		super();
	}

	public NoClientsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoClientsException(String message) {
		super(message);
	}

	public NoClientsException(Throwable cause) {
		super(cause);
	}

}
