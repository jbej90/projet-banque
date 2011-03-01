package com.excilys.projet.banque.dao.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.utils.DataSet;
import com.excilys.projet.banque.dao.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class AuthDAOImplTest {

	@Resource(name = "authDao")
	private AuthDAOImpl authDAOImpl;
	@Resource(name = "clientDao")
	private ClientDAOImpl clientDAOImpl;

	@Test
	public void findAllTest() {
		assertFalse(authDAOImpl.findAll().isEmpty());
	}

	@Test
	public void saveTest() {
		Auth auth = new Auth();
		auth.setLogin("login");
		auth.setPassword("test");
		auth.setEnabled(1);
		Client client = clientDAOImpl.findById(2);
		auth.setClient(client);
		int currentSize = authDAOImpl.findAll().size();
		authDAOImpl.save(auth);
		assertTrue(authDAOImpl.findAll().size() == currentSize + 1);

	}

	@Test
	public void findByIdTest() {
		Auth auth = new Auth();
		auth.setLogin("login");
		auth.setPassword("fraise");
		auth.setEnabled(1);
		Client client = clientDAOImpl.findById(1);
		auth.setClient(client);
		authDAOImpl.save(auth);
		assertTrue("Echec du equals", auth.equals(authDAOImpl.findById(3)));
	}

	@Test
	public void findByLoginTest() {
		Auth auth = new Auth();
		auth.setLogin("loginSimple");
		auth.setPassword("fraise");
		auth.setEnabled(1);
		Client client = clientDAOImpl.findById(0);
		auth.setClient(client);
		authDAOImpl.save(auth);
		Auth authRecup = authDAOImpl.findByLogin("loginSimple");
		assertTrue("Echec du equals", auth.equals(authRecup));
	}

	@Test
	public void findAuthByClientTest() {
		Auth auth = new Auth();
		auth.setLogin("login");
		auth.setPassword("fraise");
		auth.setEnabled(1);
		Client client = clientDAOImpl.findById(2);
		auth.setClient(client);
		authDAOImpl.save(auth);
		Auth authRecup = authDAOImpl.findAuthByIdClient(client.getId());
		assertTrue("Echec du equals", auth.equals(authRecup));

	}
}
