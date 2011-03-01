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
import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.model.TypeCarte;

@DataSet("classpath:context/dataSet.xml")
@ContextConfiguration({ "classpath:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class CarteDAOImplTest {

	@Resource(name = "carteDao")
	private CarteDAOImpl carteDAOImpl;
	@Resource(name = "compteDao")
	private CompteDAOImpl compteDAOImpl;

	@Test
	public void saveTest() {
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
		Compte compte = compteDAOImpl.findById(1);
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
		carte.setId(1);
		carte.setNumCarte("4444331122223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());

		carteDAOImpl.save(carte);
		carteDAOImpl.findById(1).equals(carte);
	}

}
