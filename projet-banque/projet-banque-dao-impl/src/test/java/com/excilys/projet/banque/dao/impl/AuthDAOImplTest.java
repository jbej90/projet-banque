package com.excilys.projet.banque.dao.impl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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

import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
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
	public void save() {
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
	@ExpectedException(IllegalArgumentException.class) 
	public void saveAuthNull() {
		authDAOImpl.save(null);
	}

	@Test
	public void findById() {
		assertTrue(authDAOImpl.findById(1).getId()==1);
		assertTrue(authDAOImpl.findById(1).getLogin().equals("log2"));
	}

	@Test
	@ExpectedException(IllegalArgumentException.class) 
	public void findByIdNegatif() {
		authDAOImpl.findById(-1);
	}

	@Test
	public void findByLogin() {
		assertTrue(authDAOImpl.findByLogin("log1").getId()==2);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class) 
	public void findByLoginVide() {
		authDAOImpl.findByLogin("");
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByLoginNull() {
		authDAOImpl.findByLogin(null);
	}

	@Test
	public void findAuthByIdClient() {
		assertTrue(authDAOImpl.findAuthByIdClient(3).getId()==2);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAuthByIdClientNegatif() {
		authDAOImpl.findAuthByIdClient(-1);
	}
	
	
}
