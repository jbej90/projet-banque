package com.excilys.projet.banque.service.api;

import java.util.Date;
import java.util.List;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public interface OperationService {

	void effectuerVirementInterne(Compte compteEmetteur, Compte compteDestinataire, float montant) throws ServiceException;

	Operation recupererOperation(int id) throws ServiceException;

	List<Operation> recupererOperations(Compte compte);

	List<Operation> recupererOperations(Compte compte, Date date);

	List<Operation> recupererOperations(Compte compte, Date date, Type type);

	List<Operation> recupererOperations(Compte compte, Date date, List<Type> types);

	List<Operation> recupererOperationsSansType(Compte compte, Date date, List<Type> types);

	List<Operation> recupererOperations(Carte carte);

	List<Operation> recupererOperations(Carte carte, Date date);

	List<Operation> recupererOperations(Type type);

	List<Operation> recupererOperations(Type type, Date date);

	float totalOperations(List<Operation> operations);

}
