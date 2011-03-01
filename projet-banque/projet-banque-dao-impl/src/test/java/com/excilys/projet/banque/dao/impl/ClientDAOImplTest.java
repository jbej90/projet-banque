package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
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

import com.excilys.projet.banque.dao.utils.DataSet;
import com.excilys.projet.banque.dao.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Client;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class ClientDAOImplTest {

	@Resource(name = "clientDao")
	private ClientDAOImpl clientDAOImpl;

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