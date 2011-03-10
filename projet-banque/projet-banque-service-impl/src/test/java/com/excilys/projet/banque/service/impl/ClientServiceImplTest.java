package com.excilys.projet.banque.service.impl;

import static junit.framework.Assert.assertTrue;

import java.util.List;

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

import com.excilys.projet.banque.dao.impl.ClientDAOImpl;
import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

@DataSet("classpath:context/projet-banque-service-impl-dataSet.xml")
@ContextConfiguration({ "classpath*:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class ClientServiceImplTest {

	@Resource(name = "clientService")
	private ClientService clientService;
	@Resource(name = "clientDao")
	private ClientDAOImpl clientDAO;

	@Test
	public void recupererListeComptesTest() {

		Client client = clientDAO.findById(1);
		List<Compte> lesComptes = clientService.recupererListeComptes(client);
		assertTrue(lesComptes.size() == 2);
		for (Compte compte : lesComptes) {
			assertTrue(compte.getClient().getId() == 1);
		}
	}

	@Test
	public void recupererClientIdTest() throws ServiceException {
		Client leClient = clientDAO.findByUsername("log2");
		assertTrue(clientService.recupererClientId("log2") == leClient.getId());
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererClientIdTestException() throws ServiceException {
		clientService.recupererClientId("aaa");
	}

	@Test
	public void recupererClientsTest() throws ServiceException {
		List<Client> lesClient = clientService.recupererClients();
		assertTrue(lesClient.size() == 3);
	}

	// @Test
	// @ExpectedException(ServiceException.class)
	// public void recupererListeComptesTest() {
	//
	// clientService.recupererListeComptes(5);
	//
	// }

}
