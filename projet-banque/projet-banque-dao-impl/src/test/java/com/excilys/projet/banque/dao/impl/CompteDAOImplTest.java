package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

public class CompteDAOImplTest {

	private static CompteDAOImpl compteDAOImpl;

	@BeforeClass
	public static void setUp() {
		compteDAOImpl = new CompteDAOImpl();
	}

	@Test(expected = UnknownCompteException.class)
	public void findByIdTest() throws UnknownCompteException {
		compteDAOImpl.findById(-1);
	}

	// TODO : optimiser le test!!!!
	@Test
	public void saveTest() throws UnknownCompteException {
		ClientDAOImpl clientDAOImpl = new ClientDAOImpl();
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
}
