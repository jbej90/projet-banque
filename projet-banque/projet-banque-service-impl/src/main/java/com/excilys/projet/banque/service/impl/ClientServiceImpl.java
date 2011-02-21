package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;

public class ClientServiceImpl implements ClientService {

	private CompteDAOImpl compteDao;

	public ClientServiceImpl() {

	}

	public ClientServiceImpl(CompteDAOImpl compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public List<Compte> recupererListeComptes(Client client) {
		return compteDao.findAllByClient(client);
	}

}
