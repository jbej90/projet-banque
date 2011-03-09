package com.excilys.projet.banque.webservice;

import java.util.List;

import javax.jws.WebService;

import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@WebService
public interface IWService {
	
	List<CompteDTO> consultationComptes(int idClient, String login, String password);
	CompteDTO consultationCompte(int idClient, int idCompte);
	List<OperationDTO> consultationOperations(int idCompte, int idClient);
	void passerOperation(int idCompteSource, int idCompteDestination, float montant);
	
}
