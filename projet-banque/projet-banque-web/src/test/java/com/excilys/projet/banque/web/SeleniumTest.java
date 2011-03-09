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
public class SeleniumTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testLogin() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Résumé de mes comptes"));
	}

	@Test
	public void testLoginDelog() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Identification"));
	}

	@Test
	public void testLoginMdpErrones() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "aaaaa");
		selenium.type("password", "aaaaa");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Login ou mot de passe erroné"));
	}

	@Test
	public void testLoginChampsVides() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Login ou mot de passe erroné"));
	}

	@Test
	public void testLoginNonActif() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test4");
		selenium.type("password", "test4");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ce compte n'est pas activé"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
