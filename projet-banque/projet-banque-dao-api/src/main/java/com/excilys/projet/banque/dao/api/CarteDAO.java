package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.TypeCarte;

public interface CarteDAO {

	List<Carte> findAll();

	List<Carte> findAllByCompte(Compte compte);
	
	List<Carte> findAllByType(TypeCarte type);
	
	Carte findById(int idCarte);
	
	void save(Carte carte);
}
