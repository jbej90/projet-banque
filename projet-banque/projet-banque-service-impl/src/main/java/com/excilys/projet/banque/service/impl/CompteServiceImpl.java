package com.excilys.projet.banque.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Service("compteService")
public class CompteServiceImpl implements CompteService {

	@Autowired
	private CompteDAO			compteDao;
	@Autowired
	private OperationService	operationService;

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
	public float totalComptes(List<Compte> comptes) {
		if (comptes == null)
			return 0;
		
		return totalComptes(new TreeSet<Compte>(comptes));
	}

	@Override
	public float totalComptes(Set<Compte> comptes) {
		if (comptes == null)
			return 0;

		float somme = 0;
		for (Compte c : comptes)
			somme += c.getSolde();
		return somme;
	}

	@Override
	public void virer(Compte source, Compte destination, float montant) throws ServiceException {
		// Effectue les controles de validité des données
		verifierAvantVirement(source, destination, montant);

		// Effectue le virement
		operationService.effectuerVirementInterne(source, destination, montant);

		// Mets à jour le solde des comptes impactés
		// TODO : A supprimer. Cette action doit être faite en batch pour toutes les opérations en cours du système
		source.setSolde(source.getSolde() - montant);
		destination.setSolde(destination.getSolde() + montant);
		compteDao.update(source);
		compteDao.update(destination);
	}

	@Override
	public void verifierAvantVirement(Compte compteEmetteur, Compte compteDestinataire, float montant) throws ServiceException {
		if (compteEmetteur == null || compteDestinataire == null)
			throw new ServiceException("Compte inexistant.");
		if (compteEmetteur.equals(compteDestinataire))
			throw new ServiceException("Compte emetteur et destinataire identiques.");
		if (!(compteEmetteur.getSolde() >= montant))
			throw new ServiceException("Solde du compte insuffisant.");
		if (montant <= 0)
			throw new ServiceException("Le montant du virement ne peut pas être nul ou négatif.");
	}

	public void setCompteDao(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}
}
