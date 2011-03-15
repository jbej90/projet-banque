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

import com.excilys.projet.banque.dao.impl.utils.DataSet;
import com.excilys.projet.banque.dao.impl.utils.DataSetTestExecutionListener;
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
	public void save() {
		Carte carte = new Carte();
		carte.setNumCarte("4444111122223333");
		carte.setType(TypeCarte.IMMEDIAT);
		carte.setDateLim(new Date());

		carteDAOImpl.save(carte);
		assertNotNull(carteDAOImpl.findById(carte.getId()));
		assertEquals(carte.getId(), carteDAOImpl.findById(carte.getId()).getId());
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void saveCarteNull() {
		carteDAOImpl.save(null);
	}

	@Test
	public void findAll() {
		assertTrue(carteDAOImpl.findAll().size()==3);
	}

	@Test
	public void findAllByIdCompte() {
		assertTrue(carteDAOImpl.findAllByIdCompte(1).size() == 2);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByIdCompteNegatif() {
		carteDAOImpl.findAllByIdCompte(-1);
	}

	@Test
	public void findAllByType() {
		assertTrue(carteDAOImpl.findAllByType(TypeCarte.IMMEDIAT).size() == 1);
		assertTrue(carteDAOImpl.findAllByType(TypeCarte.DIFFERE).size() == 2);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findAllByTypeNull() {
		carteDAOImpl.findAllByType(null);
	}

	@Test
	public void findById() {
		assertTrue(carteDAOImpl.findById(1).getId()==1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void findByIdNegatif() {
		carteDAOImpl.findById(-1);
	}

}
