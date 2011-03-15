package com.excilys.projet.banque.service.api.exception;

public class SimilarAccountsException extends Exception {

	public SimilarAccountsException() {
		super();
	}

	public SimilarAccountsException(String message, Throwable cause) {
		super(message, cause);
	}

	public SimilarAccountsException(String message) {
		super(message);
	}

	public SimilarAccountsException(Throwable cause) {
		super(cause);
	}

}
