package com.excilys.projet.banque.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exception.InsufficientBalanceException;
import com.excilys.projet.banque.service.api.exception.SimilarAccountsException;
import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.ComptesDTO;

@WebService(endpointInterface = "com.excilys.projet.banque.webservice.IWService")
public class SoapService implements IWService {

	@Autowired
	private CompteService		compteService;
	@Autowired
	private OperationService	operationService;
	@Autowired
	private ClientService		clientService;
	@Autowired
	private ConversionService	converter;

	public SoapService() {
	}

	@Override
	public ComptesDTO consultationComptes(int idClient) {
		return null;
	}

	@Override
	public String passerOperation(int idCompteEmetteur, int idCompteDestinataire, float montant) {
		Compte compteEmetteur = compteService.recupererCompte(idCompteEmetteur);
		Compte compteDestinataire = compteService.recupererCompte(idCompteDestinataire);
		try {
			compteService.virer(compteEmetteur, compteDestinataire, montant);
		}
		catch (SimilarAccountsException e) {
			e.printStackTrace();
			return "Opération échouée!";
		}
		catch (InsufficientBalanceException e) {
			e.printStackTrace();
			return "Opération échouée!";
		}
		return "Opération réussie!";
	}

	@Override
	public CompteDTO consultationCompte(int idClient, int idCompte) {
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
