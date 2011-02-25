package com.excilys.projet.banque.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.TypeCarte;

public class CarteDAOImplTest {

	private static CarteDAOImpl	carteDAOImpl;
	private static Logger logger = LoggerFactory.getLogger(CarteDAOImpl.class);

	@BeforeClass
	public static void setUp() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		carteDAOImpl = appContext.getBean("carteDao", CarteDAOImpl.class);
		
		logger.debug("plop");
	}

	@Test
	public void saveTest() {
		logger.debug("plop test");
		
		Carte carte = new Carte();
		carte.setNumCarte("4444111122223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());
		
		carteDAOImpl.save(carte);
		assertNotNull(carteDAOImpl.findById(carte.getId()));
		assertEquals(carte.getId(), carteDAOImpl.findById(carte.getId()).getId());
	}
	
	@Test
	public void findAll() {
		Carte carte = new Carte();
		carte.setNumCarte("4445511122223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());
		
		int currentSize = carteDAOImpl.findAll().size();
		carteDAOImpl.save(carte);
		assertTrue(carteDAOImpl.findAll().size() == currentSize + 1);
	}

	@Test
	public void findAllByCompte() {
		Client client = new Client();
        client.setNom("test");
        client.setPrenom("test");
        client.setAdresse("test");
        client.setDateLastConnection(new Date());
		
		Compte compte = new Compte();
		compte.setLibelle("compte");
		compte.setSolde(10);
		compte.setClient(client);
		
		Carte carte = new Carte();
		carte.setNumCarte("4444111155223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());
		carte.setCompte(compte);
		
		int currentSize = carteDAOImpl.findAllByCompte(compte).size();
		carteDAOImpl.save(carte);
		assertTrue(carteDAOImpl.findAllByCompte(compte).size() == currentSize + 1);
	}

	@Test
	public void findAllByType() {
		Carte carte = new Carte();
		carte.setNumCarte("4444111122223533");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());
		
		int currentSize = carteDAOImpl.findAllByType(TypeCarte.IMMEDIAT).size();
		carteDAOImpl.save(carte);
		assertTrue(carteDAOImpl.findAllByType(TypeCarte.IMMEDIAT).size() == currentSize + 1);
	}

	@Test
	public void findById() {
		Carte carte = new Carte();
		carte.setId(10);
		carte.setNumCarte("4444331122223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());
		
		carteDAOImpl.save(carte);
		carteDAOImpl.findById(10).equals(carte);
	}

}
