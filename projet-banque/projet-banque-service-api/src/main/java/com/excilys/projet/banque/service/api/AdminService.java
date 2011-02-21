package com.excilys.projet.banque.service.api;

import com.excilys.projet.banque.model.Client;

public interface AdminService {

	void ajouterClient(Client client);

	void supprimerClient(Client client);

}
