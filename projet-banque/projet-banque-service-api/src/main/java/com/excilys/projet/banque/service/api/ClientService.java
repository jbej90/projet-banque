package com.excilys.projet.banque.service.api;

import java.util.List;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public interface ClientService {

	List<Compte> recupererListeComptes(Client client);
}
