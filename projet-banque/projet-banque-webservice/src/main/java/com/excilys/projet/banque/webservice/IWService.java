package com.excilys.projet.banque.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.ComptesDTO;

@WebService
public interface IWService {

	ComptesDTO consultationComptes(@WebParam(name = "idClient") int idClient);

	CompteDTO consultationCompte(@WebParam(name = "idClient") int idClient, @WebParam(name = "idCompte") int idCompte);

	String passerOperation(@WebParam(name = "idCompteEmetteur") int idCompteEmetteur, @WebParam(name = "idCompteDestinataire") int idCompteDestinataire,
			@WebParam(name = "montant") float montant);

}
