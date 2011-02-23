package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Client;

public interface ClientDAO {

	List<Client> findAll();

	Client findById(int idClient);

	Client findByUsername(String username);

	void save(Client client);

	List<Client> findByNom(String nom);

}
