package com.excilys.projet.banque.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@WebService
public interface IWService {

	List<CompteDTO> consultationComptes(@WebParam(name = "idClient") int idClient);

	CompteDTO consultationCompte(@WebParam(name = "idClient") int idClient, @WebParam(name = "idCompte") int idCompte);

	List<OperationDTO> consultationOperations(@WebParam(name = "idClient") int idClient, @WebParam(name = "idCompte") int idCompte);

	String passerOperation(@WebParam(name = "idCompteEmetteur") int idCompteEmetteur, @WebParam(name = "idCompteDestinataire") int idCompteDestinataire,
			@WebParam(name = "montant") float montant);

}
