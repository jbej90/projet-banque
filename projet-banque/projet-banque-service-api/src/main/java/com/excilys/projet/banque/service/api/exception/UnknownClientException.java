package com.excilys.projet.banque.service.api.exception;

public class UnknownClientException extends Exception {

	public UnknownClientException() {
		super();
	}

	public UnknownClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownClientException(String message) {
		super(message);
	}

	public UnknownClientException(Throwable cause) {
		super(cause);
	}

}
