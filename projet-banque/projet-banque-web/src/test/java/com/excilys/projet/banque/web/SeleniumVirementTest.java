package com.excilys.projet.banque.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public class SeleniumVirementTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium(SeleniumUtil.URLHOST, 4444, "*firefox", SeleniumUtil.URL);
		selenium.start();
		
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", SeleniumUtil.LOGIN);
		selenium.type("password", SeleniumUtil.PASS);
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
	}

	@Test
	/**
	 * Ne pas pouvoir faire un virement d'un montant supérieur au solde du compte émetteur
	 */
	public void testCDR3() throws Exception {
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='virement']");
		selenium.waitForPageToLoad("30000");
		selenium.type("montant", "50000");
		selenium.click("submitVirement");
		selenium.waitForPageToLoad("100000");
		verifyTrue(selenium.isTextPresent("Solde du compte insuffisant."));
	}

	@Test
	/**
	 * La liste des comptes destinataire ne doit pas contenir le compte émetteur
	 */
	public void testCDR4() throws Exception {
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='virement']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(SeleniumUtil.COMPTE_1_LIBELLE));
		verifyTrue(selenium.isTextPresent(SeleniumUtil.COMPTE_3_LIBELLE));
		verifyTrue(!selenium.isTextPresent(SeleniumUtil.COMPTE_2_LIBELLE));
	}

	@Test
	/**
	 * Virement effectué correctement et vérification de l'ajout des opérations dans les deux comptes
	 */
	public void testCDR5() throws Exception {
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='virement']");
		selenium.waitForPageToLoad("100000");

		int nbLigneDeVirementAvant = selenium.getXpathCount("//table[@id='operations']/tbody/tr").intValue();
		System.out.println(nbLigneDeVirementAvant);

		selenium.type("montant", "0.01");
		selenium.click("submitVirement");
		selenium.waitForPageToLoad("100000");

		int nbLigneDeVirementApres = selenium.getXpathCount("//table[@id='operations']/tbody/tr").intValue();
		System.out.println(nbLigneDeVirementApres);
		verifyTrue(nbLigneDeVirementAvant + 2 == nbLigneDeVirementApres);

		selenium.click("link=" + SeleniumUtil.MENU_RESUMES);
		selenium.waitForPageToLoad("30000");
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("100000");

		verifyEquals("-0,01€", selenium.getText("//table[@id='operations']/tbody/tr[1]/td[4]"));

		selenium.click("link=" + SeleniumUtil.MENU_RESUMES);
		selenium.waitForPageToLoad("30000");
		selenium.click("link="+ SeleniumUtil.COMPTE_1_LIBELLE);
		selenium.waitForPageToLoad("100000");

		verifyEquals("0,01€", selenium.getText("//table[@id='operations']/tbody/tr[1]/td[5]"));

		selenium.click("//a[@id='virement']");
		selenium.waitForPageToLoad("30000");
		selenium.type("montant", "0.01");
		selenium.click("submitVirement");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=" + SeleniumUtil.MENU_RESUMES);
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("<< tearDown");
		
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
		selenium.stop();
		
		System.out.println("tearDown >>");
	}

}
