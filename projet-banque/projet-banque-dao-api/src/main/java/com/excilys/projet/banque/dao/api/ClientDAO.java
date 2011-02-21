package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.dao.api.exceptions.EmptyClientsException;
import com.excilys.projet.banque.dao.api.exceptions.UnknownClientException;
import com.excilys.projet.banque.model.Client;

public interface ClientDAO {

	List<Client> findAll() throws EmptyClientsException;

	Client findById(int idClient) throws UnknownClientException;

	void save(Client client);

}
