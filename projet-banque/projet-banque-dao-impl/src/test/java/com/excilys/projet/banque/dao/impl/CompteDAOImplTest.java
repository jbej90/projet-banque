package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public class CompteDAOImplTest {

	private static CompteDAOImpl compteDAOImpl;
	private static ClientDAOImpl clientDAOImpl;

	@BeforeClass
	public static void setUp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		compteDAOImpl = applicationContext.getBean("compteDao", CompteDAOImpl.class);
		clientDAOImpl = applicationContext.getBean("clientDao", ClientDAOImpl.class);
	}

	// TODO : optimiser le test!!!!
	@Test
	public void saveTest() throws UnknownCompteException {
		Client client = new Client();
		client.setNom("test");
		client.setPrenom("test");
		client.setAdresse("test");
		client.setDateLastConnection(new Date());
		clientDAOImpl.save(client);

		Compte compte = new Compte();
		compte.setLibelle("test");
		compte.setClient(client);
		compteDAOImpl.save(compte);
		assertNotNull(compteDAOImpl.findById(compte.getId()));
	}

	@Test
	public void findAllTest() {
		Client client = new Client();
		client.setNom("test");
		client.setPrenom("test");
		client.setAdresse("test");
		client.setDateLastConnection(new Date());
		clientDAOImpl.save(client);

		Compte compte = new Compte();
		compte.setClient(client);
		compte.setLibelle("compte");
		compte.setSolde(0);

		int currentSize = compteDAOImpl.findAll().size();
		compteDAOImpl.save(compte);
		assertTrue(compteDAOImpl.findAll().size() == currentSize + 1);
	}

	@Test
	public void findAllByClientTest() {
		Client client = new Client();
		client.setNom("dupont");
		client.setPrenom("martin");
		client.setAdresse("chez lui");
		client.setDateLastConnection(new Date());
		clientDAOImpl.save(client);

		Compte compte = new Compte();
		compte.setClient(client);
		compte.setLibelle("compte");
		compte.setSolde(100);
		compteDAOImpl.save(compte);

		assertEquals(100f, compteDAOImpl.findAllByClient(client).get(0).getSolde());

	}
}
