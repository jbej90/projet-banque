package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.impl.ClientDAOImpl;
import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class ClientServiceImpl implements ClientService {

	private CompteDAOImpl compteDao;
	private ClientDAOImpl clientDao;

	public ClientServiceImpl() {
	}

	public ClientServiceImpl(CompteDAOImpl compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public List<Compte> recupererListeComptes(Client client) {
		return compteDao.findAllByClient(client);
	}

	@Override
	public Client recupererClient(int idClient) throws ServiceException {
		Client client = clientDao.findById(idClient);
		if (client == null)
			throw new ServiceException("Le client n'existe pas.");
		return client;
	}

	@Override
	public Client recupererClient(String username) throws ServiceException {
		Client client = clientDao.findByUsername(username);
		if (client == null)
			throw new ServiceException("Le client n'existe pas.");
		return client;
	}

	@Override
	public List<Client> recupererClients() throws ServiceException {
		List<Client> clients = clientDao.findAll();
		if (clients.isEmpty())
			throw new ServiceException("Aucun client.");
		return clientDao.findAll();
	}

	public void setCompteDao(CompteDAOImpl compteDao) {
		this.compteDao = compteDao;
	}

	public void setClientDao(ClientDAOImpl clientDao) {
		this.clientDao = clientDao;
	}
}
