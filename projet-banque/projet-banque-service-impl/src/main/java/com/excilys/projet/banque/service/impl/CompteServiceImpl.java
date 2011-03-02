package com.excilys.projet.banque.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class CompteServiceImpl implements CompteService {

	private CompteDAO compteDao;

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

	@Override
	public float totalComptes(List<Compte> comptes) throws ServiceException {
		return totalComptes(new TreeSet<Compte>(comptes));

		// if (comptes == null)
		// throw new ServiceException("Liste de comptes inexistante.");
		// float somme = 0;
		// for (Compte c : comptes)
		// somme += c.getSolde();
		// return somme;
	}

	@Override
	public float totalComptes(Set<Compte> comptes) throws ServiceException {
		if (comptes == null)
			throw new ServiceException("Liste de comptes inexistante.");
		float somme = 0;
		for (Compte c : comptes)
			somme += c.getSolde();
		return somme;
	}

	public boolean virer(Compte source, Compte destination, float montant) throws ServiceException {
		OperationServiceImpl operationServiceImpl = new OperationServiceImpl();
		if (verifierAvantVirement(source, destination, montant)) {
			operationServiceImpl.effectuerVirementInterne(source, destination, montant);
		}
		return false;
	}

	public void setCompteDao(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public boolean verifierAvantVirement(Compte compteEmetteur, Compte compteDestinataire, float montant) throws ServiceException {
		if (compteEmetteur == null || compteDestinataire == null)
			throw new ServiceException("Compte null. ");
		if (!(compteEmetteur.getSolde() >= montant))
			throw new ServiceException("Solde du compte insuffisant. ");
		if (montant <= 0)
			throw new ServiceException("Le montant du virement ne peut pas être nul ou négatif. ");

		return true;
	}
}
