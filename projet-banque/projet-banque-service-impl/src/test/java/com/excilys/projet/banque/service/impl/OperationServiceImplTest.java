package com.excilys.projet.banque.service.impl;

import static junit.framework.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;

@DataSet("classpath:context/projet-banque-service-impl-dataSet.xml")
@ContextConfiguration({ "classpath*:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class OperationServiceImplTest {

	@Resource(name = "operationService")
	private OperationService	operationService;
	@Resource(name = "compteDao")
	private CompteDAOImpl		compteDao;
//	@Resource(name = "clientDao")
//	private ClientDAOImpl		clientDao;

	@Test
	public void recupererOperation() {
		assertTrue(operationService.recupererOperation(1).getId() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void recupererOperationWithIdOperationInexistant() {
		operationService.recupererOperation(5);
	}

	@Test
	public void totalOperationsListWithOperationNull() {
		List<Operation> lesOperations = null;
		assertTrue(operationService.totalOperations(lesOperations) == 0);
	}

	@Test
	public void recupererOperationsClient() {
		assertTrue(operationService.recupererOperationsClient(1, new DateTime(2010, 10, 10, 0, 0, 0, 0).toDate(), new Type[] { Type.OP_CARTE_DIFF },
				new EtatOperation[] { EtatOperation.EN_COURS }).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void recupererOperationsClientWithMauvaisParam() {
		assertTrue(operationService.recupererOperationsClient(-1, new DateTime(2010, 10, 10, 0, 0, 0, 0).toDate(), new Type[] { Type.OP_CARTE_DIFF },
				new EtatOperation[] { EtatOperation.EN_COURS }).size() == 0);
	}

	@Test
	public void recupererOperationsCompte()  {
		assertTrue(operationService.recupererOperationsCompte(1, new DateTime(2010, 10, 10, 0, 0, 0, 0).toDate(), new Type[] { Type.OP_CARTE_DIFF },
				new EtatOperation[] { EtatOperation.EN_COURS }).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void recupererOperationsCompteWithDateNull()  {
		assertTrue(operationService.recupererOperationsCompte(1, null, new Type[] { Type.OP_CARTE_DIFF },
				new EtatOperation[] { EtatOperation.EN_COURS }).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void recupererOperationsCompteWithMauvaisParam() {
		assertTrue(operationService.recupererOperationsCompte(-1, new DateTime(2010, 10, 10, 0, 0, 0, 0).toDate(), new Type[] { Type.OP_CARTE_DIFF },
				new EtatOperation[] { EtatOperation.EN_COURS }).size() == 0);
	}
	
	@Test
	public void totalOperations() {

		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> lesOperations = operationService.recupererOperationsCompte(1, date);
		assertTrue("La valeur de la somme retournée n'est pas bonne", operationService.totalOperations(lesOperations) == 300f);

	}

	@Test
	public void effectuerVirementInterne() {
		// 1 3
		Compte compteEmetteur = compteDao.findById(1);
		Compte compteDestinataire = compteDao.findById(3);

		operationService.effectuerVirementInterne(compteEmetteur, compteDestinataire, 100f);

		List<Operation> operationsEmetteur = operationService.recupererOperationsCompte(1, new DateTime().toDate());
		List<Operation> operationsDestinataire = operationService.recupererOperationsCompte(3, new DateTime().toDate());

		assertTrue(operationsEmetteur.size() == 1);
		assertTrue(operationsDestinataire.size() == 1);

		float montantEmetteur = operationsEmetteur.get(0).getMontant();
		float montantDestinataire = operationsDestinataire.get(0).getMontant();

		assertTrue(montantEmetteur < 0);
		assertTrue(montantDestinataire > 0);
		assertTrue(Math.abs(montantEmetteur) == Math.abs(montantDestinataire));
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void effectuerVirementInterneWithCompteDestinataireNull() {
		Compte compteEmetteur = compteDao.findById(1);

		operationService.effectuerVirementInterne(compteEmetteur, null, 100f);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void effectuerVirementInterneWithCompteEmetteurNull() {
		Compte compteDestinaire = compteDao.findById(1);

		operationService.effectuerVirementInterne(null, compteDestinaire, 100f);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void effectuerVirementInterneWithMontantNegatif() {
		Compte compteDestinaire = compteDao.findById(1);
		Compte compteEmetteur = compteDao.findById(3);

		operationService.effectuerVirementInterne(compteEmetteur, compteDestinaire, -1);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void effectuerVirementInterneWithComptesIdentiques() {
		Compte compteDestinaire = compteDao.findById(1);

		operationService.effectuerVirementInterne(compteDestinaire, compteDestinaire, -1);
	}

}
