package com.excilys.projet.banque.dao.api;

import java.util.Date;
import java.util.List;

import com.excilys.projet.banque.dao.api.exceptions.DAOException;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;

public interface OperationDAO {

	void update(Operation operation);

	void save(Operation operation);
	
	Operation findById(int idOperation);

	List<Operation> findAll();
	
	List<Operation> findAllByCompte(int idCompte, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) throws DAOException;
	
	List<Operation> findAllByClient(int idClient, Type[] types, EtatOperation[] etats, Date dateDebut, Date dateFin) throws DAOException;
	
//	List<Operation> findAllByCompte(Compte compte);
//
//	List<Operation> findAllByCarte(Carte carte);
//
//	List<Operation> findAllByClient(Date date, Client client);
//
//	List<Operation> findAllByClientByType(Date date, Client client, Type type);
//
//	List<Operation> findAllByMoisByCompte(Date date, Compte compte);
//
//	List<Operation> findAllByMoisByCompteAndByType(Date date, Compte compte, Type type);
//
//	List<Operation> findAllByMoisByCompteAndByTypes(Date date, Compte compte, List<Type> types);
//
//	List<Operation> findAllByMoisByCompteAndNotInTypes(Date date, Compte compte, List<Type> types);

}
