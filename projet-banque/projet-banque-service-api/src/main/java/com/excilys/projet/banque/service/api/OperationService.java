package com.excilys.projet.banque.service.api;

import java.util.List;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;


public interface OperationService {
	
	Operation recupererOperation(int id) throws ServiceException;
	
	List<Operation> recupererOperations(Compte compte) throws ServiceException;
	
	List<Operation> recupererOperations(Carte carte) throws ServiceException;
	
	List<Operation> recupererOperations(Type type) throws ServiceException;
	
}
