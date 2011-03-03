package com.excilys.projet.banque.service.impl;

import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
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
import com.excilys.projet.banque.dao.impl.OperationDAOImpl;
import com.excilys.projet.banque.dao.utils.DataSet;
import com.excilys.projet.banque.dao.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.service.api.OperationService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@DataSet("classpath:context/projet-banque-service-impl-dataSet.xml")
@ContextConfiguration({ "classpath*:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
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
	public void recupererOperationsParamCompteDateTest() throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(compte, date);
		assertTrue("Il n'y a pas deux opérations trouvées", operations.size() == 1);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTestException() throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date);
	}

	@Test
	public void recupererOperationsParamCompteDateTypeTest() throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(compte, date, Type.OP_CARTE_DIFF);
		assertTrue("Il n'y a pas deux opérations trouvées", operations.size() == 1);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTypeTestExceptionTypeMauvais() throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(compte, date, Type.DEPOT);
		assertTrue("Il n'y a pas deux opérations trouvées", operations.size() == 1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateTypeTestExceptionDateMauvaise() throws ServiceException {
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(compte, date, Type.OP_CARTE_DIFF);
		assertTrue("Il n'y a pas deux opérations trouvées", operations.size() == 1);
	}

	@Test
	public void recupererOperationsParamCompteDateListTypeTest() throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		List<Operation> operations = operationService.recupererOperations(compte, date, lesTypes);
		assertTrue("Il n'y a pas deux opérations trouvées", operations.size() == 1);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionListTypeVide() throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionListTypeMauvais() throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.RETRAIT);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(2010, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererOperationsParamCompteDateListTypeTestExceptionDateMauvaise() throws ServiceException {
		List<Type> lesTypes = new ArrayList<Type>();
		lesTypes.add(Type.DEPOT);
		lesTypes.add(Type.OP_CARTE_DIFF);
		Compte compte = compteDao.findById(1);
		DateTime dateJoda = new DateTime(1000, 10, 9, 0, 0, 0, 0);
		Date date = dateJoda.toDate();
		operationService.recupererOperations(compte, date, lesTypes);

	}

}
