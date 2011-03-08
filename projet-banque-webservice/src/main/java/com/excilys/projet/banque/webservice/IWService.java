package com.excilys.projet.banque.webservice;

import javax.jws.WebService;

@WebService
public interface IWService {
	
	String consultationComptes(int idClient);
	String consultationCompte(int idClient, int idCompte);
	String consultationOperations(int idCompte, int idClient);
	String passerOperation(int idCompte, float montant);
	
}
