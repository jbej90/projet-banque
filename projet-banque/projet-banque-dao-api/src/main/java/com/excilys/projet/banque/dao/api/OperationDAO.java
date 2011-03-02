package com.excilys.projet.banque.dao.api;

import java.util.Date;
import java.util.List;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

public interface OperationDAO {

	Operation findById(int idOperation);

	void save(Operation operation);

	List<Operation> findAll();

	List<Operation> findAllByType(Type type);

	List<Operation> findAllByCompte(Compte compte);

	List<Operation> findAllByCarte(Carte carte);

	List<Operation> findAllByMoisByCompte(Date date, Compte compte);

	List<Operation> findAllByMoisByCompteAndByType(Date date, Compte compte, Type type);

	List<Operation> findAllByMoisByCompteAndByTypes(Date date, Compte compte, List<Type> types);

	List<Operation> findAllByMoisByCompteAndNotInTypes(Date date, Compte compte, List<Type> types);

}
