package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

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
	public void save() {
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
	@ExpectedException(IllegalArgumentException.class)
	public void saveCompteNull() {
		compteDAOImpl.save(null);
	}

	@Test
	public void findAll() {
		assertTrue(compteDAOImpl.findAll().size()==2);
	}

	@Test
	public void findAllByIdClient() {
		assertTrue(compteDAOImpl.findAllByIdClient(1).size()==1);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByIdClientNegatif() {
		compteDAOImpl.findAllByIdClient(-1);
	}
	
	@Test
	public void findById() {
		assertTrue(compteDAOImpl.findById(1).getId()==1);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByIdNegatif() {
		compteDAOImpl.findById(-1);
	}

	@Test
	public void update() {
		Compte compte=compteDAOImpl.findById(1);
		float ancienSolde = compte.getSolde();
		compte.setSolde(20f);
		compteDAOImpl.update(compte);
		assertTrue(compteDAOImpl.findById(1).getSolde() == (ancienSolde+20f));
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void updateCompteNull() {
		compteDAOImpl.update(null);
	}
}
