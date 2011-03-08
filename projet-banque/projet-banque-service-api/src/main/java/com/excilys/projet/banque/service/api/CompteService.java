package com.excilys.projet.banque.service.api;

import java.util.List;
import java.util.Set;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public interface CompteService {

	Compte recupererCompte(int id) throws ServiceException;

	List<Compte> recupererComptes() throws ServiceException;

	float totalComptes(List<Compte> comptes);

	float totalComptes(Set<Compte> comptes);

	void virer(Compte source, Compte destination, float montant) throws ServiceException;

	void verifierAvantVirement(Compte source, Compte destination, float montant) throws ServiceException;

}
