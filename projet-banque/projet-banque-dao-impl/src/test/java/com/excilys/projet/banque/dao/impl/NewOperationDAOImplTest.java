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

import com.excilys.projet.banque.dao.api.exceptions.DAOException;
import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Type;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class NewOperationDAOImplTest {

	@Resource(name = "operationDao")
	private OperationDAOImpl	operationDAOImpl;

	@Test
	public void findAllByCompteTest() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByCompteTestIdCompteNegatif() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(-1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByCompteTestDateDebutApresDateFin() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByCompteTestDateDebutNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS }, null,
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByCompteTestDateFinNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), null).size() == 1);
	}

	@Test
	public void findAllByCompteTestTypeNullEtEtatNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, null, null, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByCompteTestTypeVideEtEtatVide() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(1, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByCompteTestCompteNExistePas() throws DAOException {
		assertTrue(operationDAOImpl.findAllByCompte(155, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 0);
	}

	// findAllByClientTest

	@Test
	public void findAllByClientTest() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByClientTestIdClientNegatif() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(-1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByClientTestDateDebutApresDateFin() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByClientTestDateDebutNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS }, null,
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate()).size() == 1);
	}

	@Test
	@ExpectedException(DAOException.class)
	public void findAllByClientTestDateFinNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, new Type[] { Type.OP_CARTE_DIFF }, new EtatOperation[] { EtatOperation.EN_COURS },
				new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), null).size() == 1);
	}

	@Test
	public void findAllByClientTestTypeNullEtEtatNull() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, null, null, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(), new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByClientTestTypeVideEtEtatVide() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(1, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 2);
	}

	@Test
	public void findAllByClientTestCompteNExistePas() throws DAOException {
		assertTrue(operationDAOImpl.findAllByClient(155, new Type[] {}, new EtatOperation[] {}, new DateTime(2010, 10, 1, 0, 0, 0, 0).toDate(),
				new DateTime(2010, 10, 31, 0, 0, 0, 0).toDate()).size() == 0);
	}
}
