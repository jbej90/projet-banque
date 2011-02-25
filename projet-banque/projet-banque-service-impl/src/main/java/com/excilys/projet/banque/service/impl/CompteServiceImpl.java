package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class CompteServiceImpl implements CompteService {

	private CompteDAO	compteDao;

	public CompteServiceImpl() {
	}

	public CompteServiceImpl(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public Compte recupererCompte(int id) throws ServiceException {
		Compte compte = compteDao.findById(id);
		if (compte == null) {
			throw new ServiceException("Le compte n'existe pas.");
		}
		return compte;
	}

	@Override
	public List<Compte> recupererComptes() throws ServiceException {
		List<Compte> comptes = compteDao.findAll();
		if (comptes.isEmpty())
			throw new ServiceException("Aucun compte.");
		return comptes;
	}

	public CompteDAO getCompteDao() {
		return compteDao;
	}

	public void setCompteDao(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}
}
