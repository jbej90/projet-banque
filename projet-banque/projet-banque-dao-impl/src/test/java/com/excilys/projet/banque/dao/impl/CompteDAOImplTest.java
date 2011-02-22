package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public class CompteDAOImplTest {

	private static CompteDAOImpl compteDAOImpl;
	private static ClientDAOImpl clientDAOImpl;

	@BeforeClass
	public static void setUp() {
		compteDAOImpl = new CompteDAOImpl();
		clientDAOImpl = new ClientDAOImpl();
	}

	@Test(expected = UnknownCompteException.class)
	public void findByIdTest() throws UnknownCompteException {
		compteDAOImpl.findById(-1);
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
		// clientDAOImpl.f

		assertEquals(100, compteDAOImpl.findAllByClient(client).get(0).getSolde());

	}
}
