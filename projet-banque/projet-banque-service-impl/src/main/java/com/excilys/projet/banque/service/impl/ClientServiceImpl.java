package com.excilys.projet.banque.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.api.AuthDAO;
import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Service("clientService")
@Transactional(readOnly=true)
public class ClientServiceImpl implements ClientService {

	@Autowired
	private CompteDAO compteDao;
	@Autowired
	private ClientDAO clientDao;
	@Autowired
	private AuthDAO authDao;

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
			throw new ServiceException("Le login d'authentification n'existe pas.");
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
	public List<Compte> recupererListeComptes(int idClient) throws ServiceException {
		return compteDao.findAllByClient(recupererClient(idClient));
	}

	@Override
	public List<Compte> recupererListeComptes(Client client) {
		return compteDao.findAllByClient(client);
	}

	public void setCompteDao(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}

	public void setClientDao(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	public void setAuthDao(AuthDAO authDao) {
		this.authDao = authDao;
	}
}
