package com.excilys.projet.banque.service.impl;

import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Ignore;
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
import com.excilys.projet.banque.dao.impl.OperationDAOImpl;
import com.excilys.projet.banque.dao.utils.DataSet;
import com.excilys.projet.banque.dao.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@DataSet("classpath:context/projet-banque-service-impl-dataSet.xml")
@ContextConfiguration({ "classpath*:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DataSetTestExecutionListener.class })
@Transactional
public class OperationServiceImplTest {

	@Resource(name = "operationService")
	private OperationService operationService;
	@Resource(name = "operationDao")
	private OperationDAOImpl operationDAO;
	@Resource(name = "compteDao")
	private CompteDAOImpl compteDao;

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationTestException() throws ServiceException {
		operationService.recupererOperation(5);
	}

	@Test
	public void recupererOperationsParamCompteDateTest()
			throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				compte, date);
		assertTrue("Il n'y a pas deux opérations trouvées",
				operations.size() == 2);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTestException()
			throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date);
	}

	@Test
	public void recupererOperationsParamCompteDateTypeTest()
			throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				compte, date, Type.OP_CARTE_DIFF);
		assertTrue("Il n'y a pas deux opérations trouvées",
				operations.size() == 1);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTypeTestExceptionTypeMauvais()
			throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				compte, date, Type.DEPOT);
		assertTrue("Il n'y a pas deux opérations trouvées",
				operations.size() == 1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTypeTestExceptionDateMauvaise()
			throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				compte, date, Type.OP_CARTE_DIFF);
		assertTrue("Il n'y a pas deux opérations trouvées",
				operations.size() == 1);
	}

	@Test
	public void recupererOperationsParamCompteDateListTypeTest()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				compte, date, lesTypes);
		assertTrue("Il n'y a pas deux opérations trouvées",
				operations.size() == 1);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionListTypeVide()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionListTypeMauvais()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.VIREMENT_EXT);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionDateMauvaise()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

	@Test
	public void recupererOperationsSansType() throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.RETRAIT);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService
				.recupererOperationsSansType(compte, date, lesTypes);
		assertTrue("Il y a trop d'opération trouvées", operations.size() == 1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsSansTypeAucunResultat()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.RETRAIT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperationsSansType(compte, date, lesTypes);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsSansTypeMauvaiseDate()
			throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperationsSansType(compte, date, lesTypes);
	}

	@Test
	public void recupererOperationsParCarteEtDateTest() throws ServiceException {

		Set<Carte> cartes = compteDao.findById(1).getCarte();
		Carte carte1 = null;
		for (Carte carte : cartes) {
			carte1 = carte;
		}
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				carte1, date);
		assertTrue("Il y a trop d'opération trouvées", operations.size() == 2);

	}

	@Ignore
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParCarteEtDateTestMauvaiseDate()
			throws ServiceException {

		Set<Carte> cartes = compteDao.findById(1).getCarte();
		Carte carte1 = null;
		for (Carte carte : cartes) {
			carte1 = carte;
		}
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				carte1, date);
		assertTrue("Il y a trop d'opération trouvées", operations.size() == 2);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParCarteEtDateTestCarteNExistePas()
			throws ServiceException {
		Carte carte1 = new Carte();
		carte1.setId(15);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(carte1, date);

	}

	@Test
	public void recupererOperationsParTypeTest() throws ServiceException {
		List<Operation> operations = operationService
				.recupererOperations(Type.RETRAIT);
		assertTrue("Il y a trop d'opération trouvées", operations.size() == 1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParTypeTestMaisPasCeTypeDansLaBase()
			throws ServiceException {
		operationService.recupererOperations(Type.VIREMENT_EXT);
	}

	@Test
	public void recupererOperationsParTypeEtDateTest() throws ServiceException {
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(
				Type.RETRAIT, date);
		assertTrue("Il y a trop d'opération trouvées", operations.size() == 1);
	}

	@Ignore
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParTypeEtDateTestDateMauvaise()
			throws ServiceException {
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(Type.RETRAIT, date);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParTypeEtDateTestAucunTypeTrouve()
			throws ServiceException {
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(Type.VIREMENT_INT, date);
	}

	@Test
	public void totalOperationsTest() throws ServiceException {
		
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> lesOperations = operationService.recupererOperations(compteDao.findById(1),date);
		assertTrue("La valeur de la somme retournée n'est pas bonne",operationService.totalOperations(lesOperations) == 300f);
	
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void totalOperationsTestListOperationNull() throws ServiceException {
		List<Operation> lesOperations = null;
		operationService.totalOperations(lesOperations);
	}
	
	@Test
	@Ignore
	//TODO REPASSER LE TEST APRÈS COMMIT ET UPDATE
	public void effectuerVirementInterne() throws ServiceException {
		//1 3 
		Compte compteEmetteur = compteDao.findById(1);
		Compte compteDestinataire = compteDao.findById(3);
		
		System.out.println(compteDestinataire.getLibelle());
		System.out.println(compteEmetteur.getLibelle());
		
		operationService.effectuerVirementInterne(compteEmetteur, compteDestinataire, 100f);
		
		assertTrue(operationService.recupererOperations(compteEmetteur).size() == 1);
		assertTrue(operationService.recupererOperations(compteDestinataire).size() ==1);
		
	}


}
