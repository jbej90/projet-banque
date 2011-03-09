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
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/");
		selenium.start();

		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
	}

	@Test
	public void testClientProprioCompte() throws Exception {
		selenium.open("/projet-banque-web/private/compte/1.htm");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Détail de mon compte"));
	}


	public void testCDR_1() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=user1, compte2");
		verifyTrue(selenium.isTextPresent("Compte non valide"));
		selenium.click("link=Retour à l'accueil");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
	}
	
	@Test
	public void testClientNonProprioCompte() throws Exception {
		selenium.open("/projet-banque-web/private/compte/350.htm");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Compte non valide"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
