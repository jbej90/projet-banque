package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.model.Client;

public class AuthDAOImplTest {

	private static AuthDAOImpl authDAOImpl;
	private static ClientDAOImpl clientDAOImpl;

	@BeforeClass
	public static void setUp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		authDAOImpl = applicationContext.getBean("authDao", AuthDAOImpl.class);
		clientDAOImpl = applicationContext.getBean("clientDao", ClientDAOImpl.class);
	}

	@Test
	public void findAllTest() {
		assertFalse(authDAOImpl.findAll().isEmpty());
	}

	@Test
	public void saveTest() {
		int rand = (int) (Math.random() * 1000);
		Auth auth = new Auth();
		auth.setLogin("login" + rand);
		auth.setPassword("test");
		auth.setEnabled(1);
		Client client = clientDAOImpl.findById(13);
		auth.setClient(client);
		int currentSize = authDAOImpl.findAll().size();
		authDAOImpl.save(auth);
		assertTrue(authDAOImpl.findAll().size() == currentSize + 1);

	}

	@Test
	public void findByIdTest() {
		int rand = (int) (Math.random() * 1000);
		Auth auth = new Auth();
		auth.setLogin("login" + rand);
		auth.setPassword("fraise");
		auth.setEnabled(1);
		int currentSize = authDAOImpl.findAll().size();
		Client client = clientDAOImpl.findById(currentSize + 1);
		auth.setClient(client);
		authDAOImpl.save(auth);
		assertTrue("Echec du equals", auth.equals(authDAOImpl.findById(currentSize + 1)));
	}

	@Test
	public void findByLoginTest() {
		int rand = (int) (Math.random() * 1000);
		Auth auth = new Auth();
		auth.setLogin("login" + rand);
		auth.setPassword("fraise");
		auth.setEnabled(1);
		int currentSize = authDAOImpl.findAll().size();
		Client client = clientDAOImpl.findById(currentSize + 1);
		auth.setClient(client);
		authDAOImpl.save(auth);
		Auth authRecup = authDAOImpl.findByLogin("login" + rand);
		assertTrue("Echec du equals", auth.equals(authRecup));
	}

	@Test
	public void findAuthByClientTest() {
		int rand = (int) (Math.random() * 1000);
		Auth auth = new Auth();
		auth.setLogin("login" + rand);
		auth.setPassword("fraise");
		auth.setEnabled(1);
		int currentSize = authDAOImpl.findAll().size();
		Client client = clientDAOImpl.findById(currentSize + 1);
		auth.setClient(client);
		authDAOImpl.save(auth);
		System.out.println(auth);
		Auth authRecup = authDAOImpl.findAuthByIdClient(client.getId());
		System.out.println(authRecup);
		assertTrue("Echec du equals", auth.equals(authRecup));
	}
}
