package com.excilys.projet.banque.service.api.exceptions;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		return "Service Exception: " + super.getMessage();
	}

}
