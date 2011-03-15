package com.excilys.projet.banque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.excilys.projet.banque.dao.api.AuthDAO;
import com.excilys.projet.banque.dao.api.ClientDAO;
import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exception.NoClientsException;
import com.excilys.projet.banque.service.api.exception.UnknownLoginException;

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
	public Client recupererClient(int idClient){
		Client client = clientDao.findById(idClient);
		
		Assert.notNull(client, "Le client n'existe pas.");
		
		return client;
	}

	@Override
	public int recupererClientId(String login) throws UnknownLoginException {
		Assert.notNull(login, "Le login ne peut être null.");	
		Assert.hasText(login, "Le login ne peut être une chaîne vide.");	
		
		Auth auth = authDao.findByLogin(login);
		if (auth == null)
			throw new UnknownLoginException();
		return auth.getClient().getId();
	}

	@Override
	public List<Client> recupererClients() throws NoClientsException {
		List<Client> clients = clientDao.findAll();
		if (clients.isEmpty())
			throw new NoClientsException();
		return clients;
	}

	@Override
	public List<Compte> recupererListeComptes(int idClient) {
		List<Compte> lesComptes = new ArrayList<Compte>();
		lesComptes = compteDao.findAllByIdClient(idClient);
		return lesComptes;
	}

	@Override
	public List<Compte> recupererListeComptes(Client client) {
		Assert.notNull(client, "Le client ne peut être null.");	
		
		return compteDao.findAllByIdClient(client.getId());
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
