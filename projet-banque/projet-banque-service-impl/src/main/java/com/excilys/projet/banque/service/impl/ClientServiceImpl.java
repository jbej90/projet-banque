package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.api.exceptions.UnknownClientException;
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
	public Client recupererClient(int username) throws ServiceException {
		try {
			return clientDao.findById(username);
		} catch (UnknownClientException e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	public List<Client> recupererClients() {
		return clientDao.findAll();
	}

}
