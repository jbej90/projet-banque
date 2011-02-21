package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.projet.banque.dao.api.exceptions.EmptyClientsException;
import com.excilys.projet.banque.dao.api.exceptions.UnknownClientException;
import com.excilys.projet.banque.model.Client;

public class ClientDAOImplTest {

	private static ClientDAOImpl clientDAOImpl;

	@BeforeClass
	public static void setUp() {
		clientDAOImpl = new ClientDAOImpl();
	}

	@Test(expected = UnknownClientException.class)
	public void findByIdTest() throws UnknownClientException {
		clientDAOImpl.findById(-1);
	}

	@Test
	public void saveTest() throws UnknownClientException {
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
	public void findAllTest() throws EmptyClientsException {

		Client client = new Client();
		client.setNom("test");
		client.setPrenom("test");
		client.setAdresse("test");
		client.setDateLastConnection(new Date());
		int currentSize = clientDAOImpl.findAll().size();
		clientDAOImpl.save(client);
		assertTrue(clientDAOImpl.findAll().size() == currentSize + 1);

	}

}