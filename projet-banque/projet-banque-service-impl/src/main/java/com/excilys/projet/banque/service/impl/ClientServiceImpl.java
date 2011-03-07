package com.excilys.projet.banque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.projet.banque.dao.api.AuthDAO;
import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	private CompteDAO compteDao;
	@Autowired
	private ClientDAO clientDao;
	@Autowired
	private AuthDAO authDao;

	public ClientServiceImpl() {
	}

	public ClientServiceImpl(CompteDAO compteDao) {
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
	// TODO CHANGER EN THROWS EXCEPTION SSI LES CONSEQUENCES SONT BENINE EN
	// MATIERE DE MODIF DE CODE
	public List<Compte> recupererListeComptes(int idClient) {
		List<Compte> lesComptes = new ArrayList<Compte>();
		try {
			lesComptes = compteDao.findAllByClient(recupererClient(idClient));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return lesComptes;
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
