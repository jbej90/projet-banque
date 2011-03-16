package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
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

import com.excilys.projet.banque.model.Client;
import com.excilys.utils.spring.test.dbunit.DataSet;
import com.excilys.utils.spring.test.dbunit.DataSetTestExecutionListener;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class ClientDAOImplTest {

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
		assertNotNull(clientDAOImpl.findById(client.getId()));
		assertEquals(client.getId(), clientDAOImpl.findById(client.getId()).getId());
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void saveClientNull() {
		clientDAOImpl.save(null);
	}

	@Test
	public void findAll() {
		assertTrue(clientDAOImpl.findAll().size() == 3);
	}

	@Test
	public void findById() {
		assertEquals("Toi", clientDAOImpl.findById(1).getNom());
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByIdNegatif() {
		clientDAOImpl.findById(-1);
	}

	@Test
	public void findByLogin() {
		assertEquals("Moi" ,clientDAOImpl.findByLogin("log1").getNom());
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByLoginNull() {
		clientDAOImpl.findByLogin(null);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByLoginVide() {
		clientDAOImpl.findByLogin("");
	}

	@Test
	public void findByNom() {
		for (Client client : clientDAOImpl.findByNom("Toi"))
			assertEquals("Toi", client.getNom());
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByNomVide() {
		clientDAOImpl.findByNom("");
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByNomNull() {
		clientDAOImpl.findByNom(null);
	}

}