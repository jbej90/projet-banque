package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.banque.dao.api.exceptions.UnknownCompteException;
import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class CompteDAOImplTest {

	@Resource(name = "compteDao")
	private CompteDAOImpl compteDAOImpl;
	@Resource(name = "clientDao")
	private ClientDAOImpl clientDAOImpl;

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

		assertTrue(100f == compteDAOImpl.findAllByClient(client).get(0).getSolde());

	}
	
	
	@Test
	public void updateTest() {
		Compte compte=compteDAOImpl.findById(1);
		float ancienSolde = compte.getSolde();
		compte.setSolde(20f);
		compteDAOImpl.update(compte);
		assertTrue(compteDAOImpl.findById(1).getSolde() == (ancienSolde+20f));
	}
}
