package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertTrue;

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

import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Type;
import com.excilys.utils.spring.test.dbunit.DataSet;
import com.excilys.utils.spring.test.dbunit.DataSetTestExecutionListener;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
//TODO Alex: Voir pour renommer les noms des méthodes ?
public class NewOperationDAOImplTest {

	@Resource(name = "operationDao")
	private OperationDAOImpl	operationDAOImpl;

	@Test
	public void findAllByCompte()   {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByCompteTestIdCompteNegatif(){
		assertTrue(operationDAOImpl.findAllByIdCompte(-1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByCompteTestDateDebutApresDateFin() {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByCompteTestDateDebutNull() {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS }, null,
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByCompteTestDateFinNull() {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), null).size() == 1);
	}

	@Test
	public void findAllByCompteTestTypeNullEtEtatNull()  {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, null, null, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByCompteTestTypeVideEtEtatVide() {
		assertTrue(operationDAOImpl.findAllByIdCompte(1, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByCompteTestCompteNExistePas() {
		assertTrue(operationDAOImpl.findAllByIdCompte(155, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 0);
	}

	// findAllByClientTest

	@Test
	public void findAllByClientTest() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByClientTestIdClientNegatif() {
		assertTrue(operationDAOImpl.findAllByIdClient(-1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByClientTestDateDebutApresDateFin() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByClientTestDateDebutNull() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS }, null,
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByClientTestDateFinNull() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), null).size() == 1);
	}

	@Test
	public void findAllByClientTestTypeNullEtEtatNull() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, null, null, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByClientTestTypeVideEtEtatVide() {
		assertTrue(operationDAOImpl.findAllByIdClient(1, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByClientTestCompteNExistePas() {
		assertTrue(operationDAOImpl.findAllByIdClient(155, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 0);
	}
}
