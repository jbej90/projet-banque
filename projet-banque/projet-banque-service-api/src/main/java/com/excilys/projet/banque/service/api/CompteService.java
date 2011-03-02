package com.excilys.projet.banque.service.api;

import java.util.List;
import java.util.Set;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public interface CompteService {

	Compte recupererCompte(int id) throws ServiceException;

	List<Compte> recupererComptes() throws ServiceException;

	float totalComptes(List<Compte> comptes) throws ServiceException;

	float totalComptes(Set<Compte> comptes) throws ServiceException;

	boolean virer(Compte source, Compte destination, float montant) throws ServiceException;

	boolean verifierAvantVirement(Compte source, Compte destination, float montant) throws ServiceException;

}
