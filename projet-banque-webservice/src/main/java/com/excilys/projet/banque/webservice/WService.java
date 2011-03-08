package com.excilys.projet.banque.webservice;

import java.util.ArrayList;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;

@WebService(endpointInterface="com.excilys.projet.banque.webservice.IWService")
public class WService implements IWService {

	@Autowired
	private CompteService compteService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private ClientService clientService;
	
	public WService() {
	}
	
	@Override
	public String consultationOperations(int idCompte, int idClient) {
		return "toto";
	}

	@Override
	public String passerOperation(int idCompte, float montant) {
		return null;
	}

	@Override
	public String consultationComptes(int idClient) {
		ArrayList<Compte> comptes = (ArrayList<Compte>) clientService.recupererListeComptes(idClient);
		return null;
	}

	@Override
	public String consultationCompte(int idClient, int idCompte) {
//		for (Compte c : clientService.recupererListeComptes(idClient)) {
//			if (c.getId()==idCompte)
//				return c;
//		}
		return null;
	}

	public void setCompteService(CompteService compteService) {
		this.compteService = compteService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	
}
