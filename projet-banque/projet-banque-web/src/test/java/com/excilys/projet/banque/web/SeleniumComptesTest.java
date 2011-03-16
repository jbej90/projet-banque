package com.excilys.projet.banque.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

//@DataSet("classpath:context/dataSet-selenium.xml")
//@ContextConfiguration({ "classpath*:context/applicationContext-selenium.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
//@Transactional
//TODO CLASSNOTFOUND
public class SeleniumComptesTest extends SeleneseTestCase {

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
	 * Pouvoir accéder à l'un de ses comptes
	 */
	public void testClientProprioCompte() throws Exception {
		selenium.open("/projet-banque-web/private/compte/" + SeleniumUtil.COMPTE_1_ID + ".htm");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Détail de mon compte"));
	}

	@Test
	/**
	 * Ne pas pouvoir acceder à un compte n'appartenant pas au client
	 */
	public void testCDR1() throws Exception {
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.open("/projet-banque-web/private/compte/" + SeleniumUtil.FOREIGN_COMPTE_ID + ".htm");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Compte non valide"));
		selenium.click("link=Retour à l'accueil");
		selenium.waitForPageToLoad("30000");
	}

	@Test
	/**
	 * Le soldes de mes comptes ne doit pas ête négatif
	 */
	public void testCDR2() throws Exception {
		int nbLigneDeCompte = selenium.getXpathCount("//table[@id='comptes']/tbody/tr").intValue();
		boolean negatif = false;
		for (int i = 1; i <= nbLigneDeCompte; i++) {
			String s = selenium.getText("//table[@id='comptes']/tbody/tr[" + i + "]/td[2]").trim().replace("€", "");
			if (s != null || Float.parseFloat(s) < 0) {
				negatif = true;
			}
		}
		verifyTrue(!negatif);
	}

	@After
	public void tearDown() throws Exception {
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
		selenium.stop();
	}

}
