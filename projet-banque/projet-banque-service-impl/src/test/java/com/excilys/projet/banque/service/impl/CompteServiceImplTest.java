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
import com.excilys.projet.banque.dao.impl.CompteDAOImpl;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.service.api.CompteService;
import com.excilys.projet.banque.service.api.exception.InsufficientBalanceException;
import com.excilys.projet.banque.service.api.exception.NoAccountsException;
import com.excilys.projet.banque.service.api.exception.SimilarAccountsException;
import com.excilys.utils.spring.test.dbunit.DataSet;
import com.excilys.utils.spring.test.dbunit.DataSetTestExecutionListener;

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
	public void recupererCompte() {
		Compte compte = compteService.recupererCompte(1);
		assertTrue(compte.getId() == 1);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void recupererCompteWithIdInexistant() {
		compteService.recupererCompte(15);
	}

	@Test
	public void recupererComptes() throws NoAccountsException {
		assertTrue(compteService.recupererComptes().size() == 3);
	}

	@Test
	public void totalComptes() {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		assertTrue(compteService.totalComptes(lesCompte) == 4830f);
	}

	@Test
	public void totalComptesWithListNull() {
		List<Compte> lesCompte = null;
		assertTrue(compteService.totalComptes(lesCompte) == 0);
	}

	@Test
	public void verifierAvantVirement() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f);
		assertTrue(true);
	}

	@Test
	@ExpectedException(SimilarAccountsException.class)
	public void verifierAvantVirementWithComptesIdentiques() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(0);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void verifierAvantVirementWithCompteDestinataireNull() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = null;
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void verifierAvantVirementWithCompteEmetteurNull() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = null;
		Compte compteDestinataire = lesCompte.get(0);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 100f);
	}

	@Test
	@ExpectedException(InsufficientBalanceException.class)
	// Implique que le compte serait en dessous de Zero, hors on ne veut pas
	public void verifierAvantVirementWithMontantTropEleve() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, 10000f);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void verifierAvantVirementWithMontantNegatif() throws SimilarAccountsException, InsufficientBalanceException {
		List<Compte> lesCompte = compteDAO.findAllByIdClient(1);
		// Solde MAx 3330
		Compte compteEmetteur = lesCompte.get(0);
		Compte compteDestinataire = lesCompte.get(1);
		compteService.verifierAvantVirement(compteEmetteur, compteDestinataire, -100f);
	}

}
