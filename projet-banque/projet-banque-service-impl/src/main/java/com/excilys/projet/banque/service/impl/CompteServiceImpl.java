package com.excilys.projet.banque.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.api.CompteDAO;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exception.InsufficientBalanceException;
import com.excilys.projet.banque.service.api.exception.NoAccountsException;
import com.excilys.projet.banque.service.api.exception.SimilarAccountsException;

@Service("compteService")
@Transactional(readOnly=true)
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
	public Compte recupererCompte(int id) {
		Compte compte = compteDao.findById(id);
		
		Assert.notNull(compte, "Le compte n'existe pas.");
		
		return compte;
	}

	@Override
	public List<Compte> recupererComptes() throws NoAccountsException {
		List<Compte> comptes = compteDao.findAll();
		if (comptes.isEmpty())
			throw new NoAccountsException();
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
	@Transactional(readOnly=false)
	public void virer(Compte source, Compte destination, float montant) throws SimilarAccountsException, InsufficientBalanceException {
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
	public void verifierAvantVirement(Compte compteEmetteur, Compte compteDestinataire, float montant) throws SimilarAccountsException, InsufficientBalanceException {
		Assert.notNull(compteEmetteur, "Le compte émetteur ne peut être null.");
		Assert.notNull(compteDestinataire, "Le compte destinataire ne peut être null.");
		Assert.isTrue(montant>0, "Le montant ne peut être inférieur à 0.");
		
		if (compteEmetteur.equals(compteDestinataire))
			throw new SimilarAccountsException();
		if (!(compteEmetteur.getSolde() >= montant))
			throw new InsufficientBalanceException();
	}

	public void setCompteDao(CompteDAO compteDao) {
		this.compteDao = compteDao;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}
}
