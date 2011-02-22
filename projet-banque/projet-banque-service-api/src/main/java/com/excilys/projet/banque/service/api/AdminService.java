package com.excilys.projet.banque.service.api;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public interface AdminService {

	void ajouterClient(Client client);

	void supprimerClient(Client client);

	void ajouterCompte(Client client, Compte compte);

	void supprimerCompte(Compte compte);

}
