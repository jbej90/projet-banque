package com.excilys.projet.banque.service.api;

import java.util.List;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;


public interface CompteService {
	
	Compte recupererCompte(int id) throws ServiceException;
	
	List<Compte> recupererComptes() throws ServiceException;
	
}
