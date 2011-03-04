package com.excilys.projet.banque.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@Repository("operationService")
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDAO operationDao;

	public OperationServiceImpl() {
	}

	public OperationServiceImpl(OperationDAO operationDao) {
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
		return recupererOperations(compte, new Date());

	}

	@Override
	public List<Operation> recupererOperations(Compte compte, Date date) throws ServiceException {

		System.out.println("recup operations");

		List<Operation> ops = operationDao.findAllByMoisByCompte(date, compte);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Compte compte, Date date, Type type) throws ServiceException {
		List<Operation> ops = operationDao.findAllByMoisByCompteAndByType(date, compte, type);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Compte compte, Date date, List<Type> types) throws ServiceException {
		List<Operation> ops = operationDao.findAllByMoisByCompteAndByTypes(date, compte, types);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperationsSansType(Compte compte, Date date, List<Type> types) throws ServiceException {
		List<Operation> ops = operationDao.findAllByMoisByCompteAndNotInTypes(date, compte, types);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Carte carte) throws ServiceException {
		return recupererOperations(carte, new Date());
	}

	@Override
	// TODO REVOIR CETTE METHODE ET ACTIVER LE TEST
	public List<Operation> recupererOperations(Carte carte, Date date) throws ServiceException {
		List<Operation> ops = operationDao.findAllByCarte(carte);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public List<Operation> recupererOperations(Type type) throws ServiceException {
		return recupererOperations(type, new Date());
	}

	@Override
	// TODO REVOIR CETTE METHODE ET ACTIVER LE TEST
	public List<Operation> recupererOperations(Type type, Date date) throws ServiceException {
		List<Operation> ops = operationDao.findAllByType(type);
		if (ops.size() == 0) {
			throw new ServiceException("Aucune opération.");
		}
		return ops;
	}

	@Override
	public float totalOperations(List<Operation> operations) throws ServiceException {
		if (operations == null)
			throw new ServiceException("Liste d'opération types inexistante.");
		float somme = 0;
		for (Operation o : operations)
			somme += o.getMontant();
		return somme;
	}

	public void setOperationDao(OperationDAO operationDao) {
		this.operationDao = operationDao;
	}

	@Override
	public void effectuerVirementInterne(Compte compteEmetteur, Compte compteDestinataire, float montant) throws ServiceException {
		Operation operationSource = new Operation();
		operationSource.setCompte(compteEmetteur);
		operationSource.setMontant(-montant);
		operationSource.setType(Type.VIREMENT_INT);
		operationSource.setDateOp(new Date());
		operationSource.setEtat(EtatOperation.EFFECTUE);
		operationSource.setLibelle("virement compte "+compteEmetteur.getId()+" -> "+compteDestinataire.getId());

		Operation operationDest = new Operation();
		operationDest.setCompte(compteDestinataire);
		operationDest.setMontant(montant);
		operationDest.setType(Type.VIREMENT_INT);
		operationDest.setDateOp(new Date());
		operationDest.setEtat(EtatOperation.EFFECTUE);
		operationDest.setLibelle("virement compte "+compteEmetteur.getId()+" -> "+compteDestinataire.getId());

		operationDao.save(operationSource);
		operationDao.save(operationDest);
	}


//	public void validationVirements(List<Operation> operationsVirements){
//		for (Operation op : operationsVirements) {
//			if(op.getType() ==  Type.VIREMENT_INT || op.getType() ==  Type.VIREMENT_EXT) {
//				if(op.getEtat() == EtatOperation.EN_COURS) {
//					Compte compte = op.getCompte();
//					compte.setSolde(compte.getSolde()+op.getMontant());
//					op.setEtat(EtatOperation.EFFECTUE);
//
//					try {
//						compteDao.update(compte);
//					} catch (Exception e) {
//						continue;
//					}
//					
//					try {
//						operationDao.update(op);
//					} catch (Exception e) {
//						
//					}
//				}
//			}
//		}
//	}
}
