package com.excilys.projet.banque.webservice;

import java.util.ArrayList;
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
public class WService implements IWService {

	@Autowired
	private CompteService		compteService;
	@Autowired
	private OperationService	operationService;
	@Autowired
	private ClientService		clientService;
	@Autowired
	private ConversionService	converter;

	public WService() {
	}

	@Override
	public List<CompteDTO> consultationComptes(int idClient, String login, String password) {
		ArrayList<CompteDTO> comptes = new ArrayList<CompteDTO>();

		try {
			for (Compte compte : clientService.recupererListeComptes(idClient))
				comptes.add(converter.convert(compte, CompteDTO.class));
		}
		catch (ServiceException e) {
			e.printStackTrace();
		}

		return comptes;
	}

	@Override
	public List<OperationDTO> consultationOperations(int idCompte, int idClient) {
		// TODO Tester que le compte appartient bien au client (idClient)
		Compte compte = null;
		// operationService.recupererOperations(compte);
		return null;
	}

	@Override
	public void passerOperation(int idCompteSource, int idCompteDestination, float montant) {
	}

	@Override
	public CompteDTO consultationCompte(int idClient, int idCompte) {
		// for (Compte c : clientService.recupererListeComptes(idClient)) {
		// if (c.getId()==idCompte)
		// return c;
		// }
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
