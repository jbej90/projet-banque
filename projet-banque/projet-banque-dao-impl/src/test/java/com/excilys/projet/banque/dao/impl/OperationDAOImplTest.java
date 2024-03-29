package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;
import com.excilys.utils.spring.test.dbunit.DataSet;
import com.excilys.utils.spring.test.dbunit.DataSetTestExecutionListener;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class OperationDAOImplTest {

	@Resource(name = "operationDao")
	private OperationDAOImpl	operationDAOImpl;
	@Resource(name = "compteDao")
	private CompteDAOImpl		compteDAOImpl;
	@Resource(name = "carteDao")
	private CarteDAOImpl		carteDAOImpl;

	@Test
	public void save() {

		Compte compte = compteDAOImpl.findById(1);
		Carte carte = carteDAOImpl.findById(1);

		Operation operation = new Operation();
		operation.setLibelle("operation");
		operation.setType(Type.RETRAIT);
		operation.setDateOp(new Date());
		operation.setEtat(EtatOperation.EFFECTUE);
		operation.setMontant(10);
		operation.setCompte(compte);
		operation.setCarte(carte);

		operationDAOImpl.save(operation);
		assertNotNull(operationDAOImpl.findById(operation.getId()));
		assertTrue(operation.getId().equals(operationDAOImpl.findById(operation.getId()).getId()));
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void saveOperationNull() {
		operationDAOImpl.save(null);
	}

	@Test
	public void findAll() {
		assertTrue(operationDAOImpl.findAll().size() == 3);
	}

	@Test
	public void findById() {
		assertNotNull(operationDAOImpl.findById(1));
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByIdNegatif() {
		operationDAOImpl.findById(-1);
	}

//	@Test
//	public void findAllByClientByTypeTest() {
//		Operation operation = operationDAOImpl.findById(1);
//		Client client = operation.getCompte().getClient();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2011-01-05");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		assertTrue(operationDAOImpl.findAllByClientByType(date, client, operation.getType()).get(0).getId() == 1);
//	}
//
//	@Test
//	public void findAllByCompteTest() {
//		Operation operation = operationDAOImpl.findById(0);
//		Compte compte = operation.getCompte();
//		assertNotNull(compte);
//		assertTrue(compte.getId() == 1);
//		assertTrue(operationDAOImpl.findAllByCompte(compte).size() == 2);
//	}
//
//	@Test
//	public void findAllByCarteTest() {
//		Carte carte = operationDAOImpl.findById(1).getCarte();
//		assertNull(carte);
//		Operation operation = operationDAOImpl.findById(0);
//		Carte carte2 = operation.getCarte();
//		assertTrue(carte2.getId() == 1);
//		assertTrue(operationDAOImpl.findAllByCarte(carte2).size() == 2);
//	}

//	@Test
//	public void findByAllByMoisByCompteTest() {
//		Operation operation = operationDAOImpl.findById(1);
//		Compte compte = operation.getCompte();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2011-01-05");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//		assertFalse(operationDAOImpl.findAllByMoisByCompte(date, compte).isEmpty());
//		assertTrue(operationDAOImpl.findAllByMoisByCompte(date, compte).size() == 1);
//	}
//
//	@Test
//	public void findByAllByMoisByCompteByTypeTest() {
//		Operation operation = operationDAOImpl.findById(1);
//		Compte compte = operation.getCompte();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2011-01-05");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//		assertFalse(operationDAOImpl.findAllByMoisByCompteAndByType(date, compte, Type.OP_CARTE_IMM).isEmpty());
//		assertTrue(operationDAOImpl.findAllByMoisByCompteAndByType(date, compte, Type.OP_CARTE_IMM).size() == 1);
//	}
//
//	@Test
//	public void findByAllByMoisByCompteByTypesTest() {
//		Operation operation = operationDAOImpl.findById(1);
//		Compte compte = operation.getCompte();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2011-01-05");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		List<Type> types = new ArrayList<Type>();
//		types.add(Type.OP_CARTE_IMM);
//		types.add(Type.OP_CARTE_DIFF);
//
//		assertFalse(operationDAOImpl.findAllByMoisByCompteAndByTypes(date, compte, types).isEmpty());
//		assertTrue(operationDAOImpl.findAllByMoisByCompteAndByTypes(date, compte, types).size() == 1);
//	}
//
//	@Test
//	public void findByAllByMoisByCompteByNotInTypesTest() {
//		Operation operation = operationDAOImpl.findById(1);
//		Compte compte = operation.getCompte();
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = null;
//		try {
//			date = sdf.parse("2011-01-05");
//		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		List<Type> types = new ArrayList<Type>();
//		types.add(Type.OP_CARTE_IMM);
//
//		assertTrue(operationDAOImpl.findAllByMoisByCompteAndNotInTypes(date, compte, types).isEmpty());
//	}
}
