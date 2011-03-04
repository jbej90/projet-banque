package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public interface CompteDAO {

	void save(Compte compte);
	
	void update(Compte compte);
	
	List<Compte> findAll();

	Compte findById(int idCompte);

	List<Compte> findAllByClient(Client client);

}
