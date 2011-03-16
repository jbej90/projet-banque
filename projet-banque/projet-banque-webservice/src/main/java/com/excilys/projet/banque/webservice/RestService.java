package com.excilys.projet.banque.webservice;

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
import com.excilys.projet.banque.webservice.dto.ComptesDTO;

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
	@Path("/consultation/{idClient}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ComptesDTO consultationComptes(@PathParam("idClient") int idClient) {
		ComptesDTO comptes = converter.convert(clientService.recupererListeComptes(idClient), ComptesDTO.class);
		System.out.println(comptes);
		return comptes;
	}

	@Override
	@Path("/compte/{idClient}/{idCompte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CompteDTO consultationCompte(@PathParam("idClient") int idClient, @PathParam("idCompte") int idCompte) {
		return null;
	}

	// @Override
	@Path("/comptes/{idClient}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test(@PathParam("idClient") int idClient) {
		return null;
	}

	@Override
	public String passerOperation(int idCompteEmetteur, int idCompteDestinataire, float montant) {
		return "";
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
