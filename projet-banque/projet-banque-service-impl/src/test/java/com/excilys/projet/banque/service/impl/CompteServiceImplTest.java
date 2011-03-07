package com.excilys.projet.banque.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
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
import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.dao.utils.DataSet;
import com.excilys.projet.banque.dao.utils.DataSetTestExecutionListener;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.exceptions.ServiceException;
import static junit.framework.Assert.assertTrue;

@DataSet("classpath:context/projet-banque-service-impl-dataSet.xml")
@ContextConfiguration({ "classpath*:context/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
@Transactional
public class CompteServiceImplTest {

	@Resource(name = "compteService")
	private CompteService	compteService;
	@Resource(name = "clientDao")
	private ClientDAOImpl	clientDAO;
	@Resource(name = "compteDao")
	private CompteDAOImpl	compteDAO;

	@Test
	public void recupererCompteTest() throws ServiceException {
		Compte compte = compteService.recupererCompte(1);
		assertTrue(compte.getId() == 1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void recupererCompteTestException() throws ServiceException {
		compteService.recupererCompte(15);
	}

	@Test
	public void recupererComptesTest() throws ServiceException {
		assertTrue(compteService.recupererComptes().size() == 3);
	}

	@Test
	public void totalComptesTest() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		assertTrue(compteService.totalComptes(lesCompte) == 4830f);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void totalComptesTestListNull() throws ServiceException {
		List<Compte> lesCompte = null;
		compteService.totalComptes(lesCompte);
	}

	@Test
	public void verifierAvantVirementTest() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		assertTrue(compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f));
	}

	
	@Test
	@ExpectedException(ServiceException.class)
	public void verifierAvantVirementTestCompteIdentique() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(0);
		assertTrue(compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f));
	}
	
	@Test
	@ExpectedException(ServiceException.class)
	public void verifierAvantVirementTestCompteDestinataireNull() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = null;
		assertTrue(compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f));
	}
	
	@Test
	@ExpectedException(ServiceException.class)
	public void verifierAvantVirementTestCompteEmetteurNull() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = null;
		Compte compteDestinataire = lesCompte.get(0);
		assertTrue(compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f));
	}

	@Test
	@ExpectedException(ServiceException.class)
	//Implique que le compte serait en dessous de Zero, hors on ne veut pas
	public void verifierAvantVirementTestMantantTropEleve() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 10000f);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void verifierAvantVirementTestMontantNegatif() throws ServiceException {
		List<Compte> lesCompte = compteDAO.findAllByClient(clientDAO.findById(1));
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, -100f);
	}

}
