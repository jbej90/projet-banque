package com.excilys.projet.banque.service.impl;

import java.util.List;

import com.excilys.projet.banque.dao.impl.OperationDAOImpl;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class OperationServiceImpl implements OperationService {

	private OperationDAOImpl	operationDao;

	public OperationServiceImpl() {
	}

	public OperationServiceImpl(OperationDAOImpl operationDao) {
		this.operationDao = operationDao;
	}

	@Override
	public Operation recupererOperation(int id) throws ServiceException {
		Operation op = operationDao.findById(id);
		if (op == null) {
			throw new ServiceException("L'opération n'existe pas.");
		}
		return op;
	}

	@Override
	public List<Operation> recupererOperations(Compte compte) throws ServiceException {
		List<Operation> ops = operationDao.findAllByCompte(compte);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Carte carte) throws ServiceException {
		List<Operation> ops = operationDao.findAllByCarte(carte);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Type type) throws ServiceException {
		List<Operation> ops = operationDao.findAllByType(type);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	public void setCompteDao(OperationDAOImpl operationDao) {
		this.operationDao = operationDao;
	}
}
