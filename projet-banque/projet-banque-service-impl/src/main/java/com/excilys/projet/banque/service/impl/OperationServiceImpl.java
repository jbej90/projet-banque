package com.excilys.projet.banque.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.dao.api.exceptions.DAOException;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import com.excilys.projet.banque.service.api.utils.CalculDateMois;

@Service("operationService")
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDAO				operationDao;

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
	public List<Operation> recupererOperationsClient(int idClient, Date date) {
		return recupererOperationsClient(idClient, date, null, null);
	}

	@Override
	public List<Operation> recupererOperationsClient(int idClient, Date date, Type[] types) {
		return recupererOperationsClient(idClient, date, types, null);
	}

	@Override
	public List<Operation> recupererOperationsClient(int idClient, Date date, Type[] types, EtatOperation[] etats) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);

		try {
			return operationDao.findAllByClient(idClient, types, etats, dateDebut.toDate(), dateFin.toDate());
		}
		catch (DAOException e) {
			return new LinkedList<Operation>();
		}
	}

	@Override
	public List<Operation> recupererOperationsClientCarte(int idClient, Date date, EtatOperation[] etats) {
		return recupererOperationsClient(idClient, date, TYPES_CARTE, etats);
	}

	@Override
	public List<Operation> recupererOperationsClientNonCarte(int idClient, Date date, EtatOperation[] etats) {
		return recupererOperationsClient(idClient, date, TYPES_NON_CARTE, etats);
	}

	@Override
	public List<Operation> recupererOperationsCompte(int idCompte, Date date) {
		return recupererOperationsCompte(idCompte, date, null, null);
	}

	@Override
	public List<Operation> recupererOperationsCompte(int idCompte, Date date, Type[] types) {
		return recupererOperationsCompte(idCompte, date, types, null);
	}

	@Override
	public List<Operation> recupererOperationsCompte(int idCompte, Date date, Type[] types, EtatOperation[] etats) {
		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);

		try {
			return operationDao.findAllByCompte(idCompte, types, etats, dateDebut.toDate(), dateFin.toDate());
		}
		catch (DAOException e) {
			return new LinkedList<Operation>();
		}
	}

	@Override
	public List<Operation> recupererOperationsCompteCarte(int idCompte, Date date, EtatOperation[] etats) {
		return recupererOperationsCompte(idCompte, date, TYPES_CARTE, etats);
	}

	@Override
	public List<Operation> recupererOperationsCompteNonCarte(int idCompte, Date date, EtatOperation[] etats) {
		return recupererOperationsCompte(idCompte, date, TYPES_NON_CARTE, etats);
	}

	/*
	 * @Override public List<Operation> recupererOperations(Compte compte) { return recupererOperations(compte, new Date()); }
	 * @Override public List<Operation> recupererOperations(Compte compte, Date date) { return operationDao.findAllByMoisByCompte(date, compte); }
	 * @Override public List<Operation> recupererOperations(Compte compte, Date date, Type type) { return operationDao.findAllByMoisByCompteAndByType(date, compte, type); }
	 * @Override public List<Operation> recupererOperations(Compte compte, Date date, List<Type> types) { return operationDao.findAllByMoisByCompteAndByTypes(date, compte, types);
	 * }
	 * @Override public List<Operation> recupererOperationsSansType(Compte compte, Date date, List<Type> types) { return operationDao.findAllByMoisByCompteAndNotInTypes(date,
	 * compte, types); }
	 * @Override public List<Operation> recupererOperations(Carte carte) { return recupererOperations(carte, new Date()); }
	 * @Override // TODO REVOIR CETTE METHODE ET ACTIVER LE TEST public List<Operation> recupererOperations(Carte carte, Date date) { return operationDao.findAllByCarte(carte); }
	 * @Override public List<Operation> recupererOperations(Client client, Type type) { return recupererOperations(client, type, new Date()); }
	 * @Override // TODO REVOIR CETTE METHODE ET ACTIVER LE TEST public List<Operation> recupererOperations(Client client, Type type, Date date) { return
	 * operationDao.findAllByClientByType(date, client, type); }
	 */

	@Override
	public float totalOperations(List<Operation> operations) {
		float somme = 0;
		if (operations == null) {
			somme = 0;
		}
		else {
			for (Operation o : operations)
				somme += o.getMontant();
		}
		return somme;
	}

	public void setOperationDao(OperationDAO operationDao) {
		this.operationDao = operationDao;
	}

	@Override
	public void effectuerVirementInterne(Compte compteEmetteur, Compte compteDestinataire, float montant) {
		Date dateOp = new Date();

		Operation operationSource = new Operation();
		operationSource.setCompte(compteEmetteur);
		operationSource.setMontant(-montant);
		operationSource.setType(Type.VIREMENT_INT);
		operationSource.setDateOp(dateOp);
		operationSource.setEtat(EtatOperation.EFFECTUE);
		operationSource.setLibelle("virement compte " + compteEmetteur.getId() + " -> " + compteDestinataire.getId());

		Operation operationDest = new Operation();
		operationDest.setCompte(compteDestinataire);
		operationDest.setMontant(montant);
		operationDest.setType(Type.VIREMENT_INT);
		operationDest.setDateOp(dateOp);
		operationDest.setEtat(EtatOperation.EFFECTUE);
		operationDest.setLibelle("virement compte " + compteEmetteur.getId() + " -> " + compteDestinataire.getId());

		operationDao.save(operationSource);
		operationDao.save(operationDest);
	}

	// public void validationVirements(List<Operation> operationsVirements){
	// for (Operation op : operationsVirements) {
	// if(op.getType() == Type.VIREMENT_INT || op.getType() == Type.VIREMENT_EXT) {
	// if(op.getEtat() == EtatOperation.EN_COURS) {
	// Compte compte = op.getCompte();
	// compte.setSolde(compte.getSolde()+op.getMontant());
	// op.setEtat(EtatOperation.EFFECTUE);
	//
	// try {
	// compteDao.update(compte);
	// } catch (Exception e) {
	// continue;
	// }
	//
	// try {
	// operationDao.update(op);
	// } catch (Exception e) {
	//
	// }
	// }
	// }
	// }
	// }
}
