package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.impl.AuthDAOImpl;
import com.excilys.projet.banque.dao.impl.ClientDAOImpl;
import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class ClientServiceImpl implements ClientService {

	private CompteDAOImpl compteDao;
	private ClientDAOImpl clientDao;
	private AuthDAOImpl authDao;

	public ClientServiceImpl() {
	}

	public ClientServiceImpl(CompteDAOImpl compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public Client recupererClient(int idClient) throws ServiceException {
		Client client = clientDao.findById(idClient);
		if (client == null)
			throw new ServiceException("Le client n'existe pas.");
		return client;
	}

	@Override
	public int recupererClientId(String username) throws ServiceException {
		Auth auth = authDao.findByLogin(username);
		if (auth == null)
			throw new ServiceException("Le compte n'existe pas.");
		return auth.getId();
	}

	@Override
	public List<Client> recupererClients() throws ServiceException {
		List<Client> clients = clientDao.findAll();
		if (clients.isEmpty())
			throw new ServiceException("Aucun client.");
		return clients;
	}

	@Override
	public List<Compte> recupererListeComptes(int idClient) {
		try {
			return compteDao.findAllByClient(recupererClient(idClient));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Compte> recupererListeComptes(Client client) {
		return compteDao.findAllByClient(client);
	}

	public void setCompteDao(CompteDAOImpl compteDao) {
		this.compteDao = compteDao;
	}

	public void setClientDao(ClientDAOImpl clientDao) {
		this.clientDao = clientDao;
	}

	public void setAuthDao(AuthDAOImpl authDao) {
		this.authDao = authDao;
	}
}
