package com.excilys.projet.banque.service.api.exception;

public class UnknownLoginException extends Exception {

	public UnknownLoginException() {
		super();
	}

	public UnknownLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownLoginException(String message) {
		super(message);
	}

	public UnknownLoginException(Throwable cause) {
		super(cause);
	}

}
