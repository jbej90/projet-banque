package com.excilys.projet.banque.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

//@DataSet("classpath:context/dataSet-selenium.xml")
//@ContextConfiguration({ "classpath:context/applicationContext-selenium.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DataSetTestExecutionListener.class })
//@Transactional
//TODO CLASSNOTFOUND
public class SeleniumTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://192.168.10.62:8080/");
		selenium.start();
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
	}

	public void testAccount() throws Exception {
		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", "test1");
		selenium.type("password", "test1");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Déconnexion");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
