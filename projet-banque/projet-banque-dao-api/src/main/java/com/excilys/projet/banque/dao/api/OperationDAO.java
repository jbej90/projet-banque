package com.excilys.projet.banque.dao.api;

import java.util.List;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

public interface OperationDAO {

	List<Operation> findAll();

	List<Operation> findAllByType(Type type);
	
	List<Operation> findAllByCompte(Compte compte);
	
	List<Operation> findAllByCarte(Carte carte);
	
	Operation findById(int idOperation);

	void save(Operation operation);
	
}