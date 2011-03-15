package com.excilys.projet.banque.service.api;

import java.util.List;
import java.util.Set;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exception.InsufficientBalanceException;
import com.excilys.projet.banque.service.api.exception.NoAccountsException;
import com.excilys.projet.banque.service.api.exception.SimilarAccountsException;

public interface CompteService {

	Compte recupererCompte(int id);

	List<Compte> recupererComptes() throws NoAccountsException;

	float totalComptes(List<Compte> comptes);

	float totalComptes(Set<Compte> comptes);

	void virer(Compte source, Compte destination, float montant) throws SimilarAccountsException, InsufficientBalanceException;

	void verifierAvantVirement(Compte source, Compte destination, float montant) throws SimilarAccountsException, InsufficientBalanceException;

}
