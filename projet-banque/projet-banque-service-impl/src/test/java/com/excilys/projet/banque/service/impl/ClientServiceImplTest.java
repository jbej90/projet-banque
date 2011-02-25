package com.excilys.projet.banque.service.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.ClientService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;

public class ClientServiceImplTest {
	private static ClientService clientService;
	
	@BeforeClass
	public static void setUp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/applicationContext.xml");
		clientService = applicationContext.getBean("clientService", ClientService.class);
	}

	@Test
	public void recupererListeComptesTest() {
		Client client = new Client();
		Set<Compte> comptes = new TreeSet<Compte>();
		comptes.add(new Compte(1, "c1", client, 10));
		comptes.add(new Compte(2, "c2", client, 20));
		client.setComptes(comptes);
		
		assertEquals(clientService.recupererListeComptes(client), comptes);
	}

	@Test
	public void recupererClientTest() {
		int client = 0;
		try {
			client = clientService.recupererClientId("test1");
		} catch (ServiceException e) {}
		assertTrue(client == 0);
		
		client = 0;
		try {
			client = clientService.recupererClientId("aaa");
		} catch (ServiceException e) {}
		assertTrue(client != 0);
	}

	@Test
	public void recupererClientsTest() {
		
	}
}
