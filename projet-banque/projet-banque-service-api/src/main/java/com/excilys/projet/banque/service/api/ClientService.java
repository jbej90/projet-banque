package com.excilys.projet.banque.service.api;

import java.util.List;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exception.NoClientsException;
import com.excilys.projet.banque.service.api.exception.UnknownClientException;
import com.excilys.projet.banque.service.api.exception.UnknownLoginException;

public interface ClientService {

	Client recupererClient(int idClient) throws UnknownClientException;

	int recupererClientId(String username) throws UnknownLoginException;

	List<Client> recupererClients() throws NoClientsException ;

	List<Compte> recupererListeComptes(int idClient) throws UnknownClientException ;
	
	List<Compte> recupererListeComptes(Client client);

}
