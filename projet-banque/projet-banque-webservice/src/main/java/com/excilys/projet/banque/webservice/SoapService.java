package com.excilys.projet.banque.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@WebService(endpointInterface = "com.excilys.projet.banque.webservice.IWService")
public class SoapService implements IWService {

	@Autowired
	private CompteService compteService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ConversionService converter;

	public SoapService() {
	}

	@Override
	public List<CompteDTO> consultationComptes(int idClient) {
		return null;
	}

	@Override
	public List<OperationDTO> consultationOperations(int idCompte, int idClient) {
		return null;
	}

	@Override
	public boolean passerOperation(int idCompteEmetteur, int idCompteDestinataire, float montant) {
		Compte compteEmetteur = null;
		Compte compteDestinataire = null;
		try {
			compteEmetteur = compteService.recupererCompte(idCompteEmetteur);
			compteDestinataire = compteService.recupererCompte(idCompteDestinataire);
			compteService.virer(compteEmetteur, compteDestinataire, montant);
			// operationService.effectuerVirementInterne(compteEmetteur,
			// compteDestinataire, montant);

		} catch (ServiceException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
