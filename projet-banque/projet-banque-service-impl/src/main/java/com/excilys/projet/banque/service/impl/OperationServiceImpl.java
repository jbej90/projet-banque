package com.excilys.projet.banque.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.api.OperationDAO;
import com.excilys.projet.banque.dao.utils.CalculDateMois;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.utils.SecurityUtils;

@Service("operationService")
@Transactional(readOnly = true)
@Secured({ SecurityUtils.ROLE_USER, SecurityUtils.ROLE_ADMIN, SecurityUtils.ROLE_OPERATOR })
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationDAO	operationDao;

	public OperationServiceImpl() {
	}

	public OperationServiceImpl(OperationDAO operationDao) {
		this.operationDao = operationDao;
	}

	@Override
	public Operation recupererOperation(int id) {
		Operation op = operationDao.findById(id);

		Assert.notNull(op, "L'opération n'existe pas.");

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

		verifierDates(dateDebut, dateFin);

		return operationDao.findAllByIdClient(idClient, types, etats, dateDebut.toDate(), dateFin.toDate());
	}

	private void verifierDates(DateTime dateDebut, DateTime dateFin) {
		Assert.notNull(dateDebut, "La date de début ne peut être null.");
		Assert.notNull(dateFin, "La date de fin ne peut être null.");
		Assert.isTrue(dateDebut.isBefore(dateFin), "La date de début ne peut être plus récente que la date de fin.");
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

		Assert.notNull(date, "La date ne peut être null.");

		DateTime dateDebut = CalculDateMois.calculDateTimeDebutMois(date);
		DateTime dateFin = CalculDateMois.calculDateTimeFinMois(date);

		verifierDates(dateDebut, dateFin);

		return operationDao.findAllByIdCompte(idCompte, types, etats, dateDebut.toDate(), dateFin.toDate());
	}

	@Override
	public List<Operation> recupererOperationsCompteCarte(int idCompte, Date date, EtatOperation[] etats) {
		return recupererOperationsCompte(idCompte, date, TYPES_CARTE, etats);
	}

	@Override
	public List<Operation> recupererOperationsCompteNonCarte(int idCompte, Date date, EtatOperation[] etats) {
		return recupererOperationsCompte(idCompte, date, TYPES_NON_CARTE, etats);
	}

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
	@Transactional(readOnly = false)
	public void effectuerVirementInterne(Compte compteEmetteur, Compte compteDestinataire, float montant) {

		Assert.notNull(compteEmetteur, "Le compte émetteur ne peut être null.");
		Assert.notNull(compteDestinataire, "Le compte destinataire ne peut être null.");
		Assert.isTrue(!compteDestinataire.equals(compteEmetteur), "Impossible d'effectuer un virement vers le même compte.");
		Assert.isTrue(montant > 0, "Le montant du virement ne peut être inférieur ou égal à 0.");

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
}
