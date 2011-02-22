package com.excilys.projet.banque.service.api;

import java.util.List;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public interface ClientService {

	Client recupererClient(int username) throws ServiceException;

	List<Client> recupererClients() throws ServiceException;

	List<Compte> recupererListeComptes(Client client);
}
