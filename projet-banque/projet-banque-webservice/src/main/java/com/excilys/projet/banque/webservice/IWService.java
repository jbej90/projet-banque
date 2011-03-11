package com.excilys.projet.banque.webservice;

import java.util.List;

import javax.jws.WebService;

import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@WebService
public interface IWService {
	
	List<CompteDTO> consultationComptes(int idClient);
	CompteDTO consultationCompte(int idClient, int idCompte);
	List<OperationDTO> consultationOperations(int idClient, int idCompte);
	boolean passerOperation(int idCompteEmetteur, int idCompteDestinataire, float montant);
	
}
