package com.excilys.projet.banque.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public class SeleniumVirementTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testCDR_3() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=user1, compte2");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Effectuer un virement");
		selenium.waitForPageToLoad("30000");
		selenium.type("montant", "5000");
		selenium.click("submitVirement");
		selenium.waitForPageToLoad("100000");
		verifyTrue(selenium.isTextPresent("Solde du compte insuffisant."));
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
	}

	@Test
	public void testCDR_4() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=user1, compte2");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Effectuer un virement");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Destination : user1, compte1"));
		verifyTrue(selenium.isTextPresent("user1, compte3"));
		verifyTrue(!selenium.isTextPresent("user1, compte2"));
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}

}
