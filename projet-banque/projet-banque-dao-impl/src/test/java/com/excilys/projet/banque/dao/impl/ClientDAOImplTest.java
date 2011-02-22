package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.model.Client;

public class ClientDAOImplTest {

	private static ClientDAOImpl clientDAOImpl;

	@BeforeClass
	public static void setUp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		clientDAOImpl = applicationContext.getBean("clientdao", ClientDAOImpl.class);
	}

	@Test
	public void saveTest() {
		Client client = new Client();
		client.setNom("test");
		client.setPrenom("test");
		client.setAdresse("test");
		client.setDateLastConnection(new Date());
		clientDAOImpl.save(client);
		assertNotNull(clientDAOImpl.findById(client.getId()));
		assertEquals(client.getId(), clientDAOImpl.findById(client.getId()).getId());
	}

	@Test
	public void findAllTest() {

		Client client = new Client();
		client.setNom("test");
		client.setPrenom("test");
		client.setAdresse("test");
		client.setDateLastConnection(new Date());
		int currentSize = clientDAOImpl.findAll().size();
		clientDAOImpl.save(client);
		assertTrue(clientDAOImpl.findAll().size() == currentSize + 1);

	}

	@Test
	public void findByNom() {
		Client client = new Client();
		client.setNom("Dupuis");
		client.setPrenom("Jean");
		client.setAdresse("Paris");
		client.setDateLastConnection(new Date());
		clientDAOImpl.save(client);

		for (Client dupuis : clientDAOImpl.findByNom("Dupuis"))
			assertEquals("Dupuis", dupuis.getNom());

	}

}