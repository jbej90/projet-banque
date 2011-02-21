package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public interface CompteDAO {

	List<Compte> findAll();

	Compte findById(int idCompte) throws UnknownCompteException;

	void save(Compte compte);

	List<Compte> findAllByClient(Client client);

}
