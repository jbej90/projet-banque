package com.excilys.projet.banque.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@Path("/")
@Component("restService")
public class RestService implements IWService {

	@Autowired
	private CompteService compteService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ConversionService converter;

	public RestService() {
	}

	@GET
	public String welcome() {
		return "Welcome!!!";
	}

	@Override
	@Path("/compte/{idClient}/{idCompte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CompteDTO consultationCompte(@PathParam("idClient") int idClient, @PathParam("idCompte") int idCompte) {
		// try {
		// Client client = clientService.recupererClient(idClient);
		// } catch (UnknownClientException e) {
		// e.printStackTrace();
		// return null;
		// }
		return null;
	}

	// @Override
	@Path("/comptes/{idClient}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test(@PathParam("idClient") int idClient) {
		return "test" + idClient;
	}

	// public List<CompteDTO> consultationComptes(int idClient) {
	// ArrayList<CompteDTO> comptes = new ArrayList<CompteDTO>();
	//
	// for (Compte compte : clientService.recupererListeComptes(idClient))
	// comptes.add(converter.convert(compte, CompteDTO.class));
	//
	// return comptes;
	// }

	@Override
	@Path("/operations/{idClient}/{idCompte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OperationDTO> consultationOperations(@PathParam("idClient") int idClient, @PathParam("idCompte") int idCompte) {
		return null;
	}

	@Override
	public boolean passerOperation(int idCompteEmetteur, int idCompteDestinataire, float montant) {
		return false;
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

	@Override
	public List<CompteDTO> consultationComptes(int idClient) {
		// TODO Auto-generated method stub
		return null;
	}

}
