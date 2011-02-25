package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.model.Type;


public class OperationDAOImplTest {

	private static OperationDAOImpl operationDAOImpl;

	@BeforeClass
	public static void setUp() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		operationDAOImpl = appContext.getBean("operationDao", OperationDAOImpl.class);
	}

	@Test
	public void saveTest() {
		Client client = new Client();
        client.setNom("test");
        client.setPrenom("test");
        client.setAdresse("test");
        client.setDateLastConnection(new Date());
		
		Compte compte = new Compte();
		compte.setLibelle("compte");
		compte.setSolde(10);
		compte.setClient(client);
		
		Type type = new Type();
		type.setLibelle("retrait");
		
		Operation operation = new Operation();
		operation.setLibelle("operation");
		operation.setType(type);
		operation.setDateOp(new Date());
		operation.setEtat(EtatOperation.EFFECTUE);
		operation.setMontant(10);
		operation.setCompte(compte);

		operationDAOImpl.save(operation);
		assertNotNull(operationDAOImpl.findById(operation.getId()));
		assertEquals(operation.getId(), operationDAOImpl.findById(operation.getId()).getId());
	}

	@Test
	public void findAllTest() {
		
	}

	@Test
	public void findAllByTypeTest() {
		
	}

	@Test
	public void findAllByCompteTest() {
		
	}

	@Test
	public void findAllByCarteTest() {
		
	}

	@Test
	public void findByIdTest() {
		
	}
}
