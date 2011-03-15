package com.excilys.projet.banque.web;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class SeleniumOperationsTest extends SeleneseTestCase {

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080/");
		selenium.start();

		selenium.open("/projet-banque-web/login.htm");
		selenium.type("username", SeleniumUtil.LOGIN);
		selenium.type("password", SeleniumUtil.PASS);
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
	}

	@Test
	/**
	 * Les opérations affichées par défaut sur le détail d'un compte sont celles du mois courant
	 */
	public void testCDR6() throws Exception {
		String[] months = DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();
		String actualMonth = months[Calendar.getInstance().get(Calendar.MONTH)];
		Pattern pattern = Pattern.compile("[0-9]+ ([^0-9]+) [0-9]+");
		
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("30000");

		int nbLigneDeCompte = selenium.getXpathCount("//table[@id='operations']/tbody/tr").intValue();
		boolean negatif = false;
		for (int i = 1; i <= nbLigneDeCompte; i++) {
			String s = selenium.getText("//table[@id='operations']/tbody/tr[" + i + "]/td[1]").trim();

			Matcher matcher = pattern.matcher(s);
			if (!matcher.find() || !matcher.group(1).equals(actualMonth)) {
				negatif = true;
			}
			
		}
		verifyTrue(!negatif);
	}

	@Test
	/**
	 * Les opérations affichées sur le détail d'un compte sont celles du mois sélectionné
	 */
	public void testCDR7() throws Exception {
		String[] months = DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();
		String actualMonth = months[Calendar.getInstance().get(Calendar.MONTH)];
		Pattern pattern = Pattern.compile("[0-9]+ ([^0-9]+) [0-9]+");
		
		selenium.click("link=" + SeleniumUtil.COMPTE_2_LIBELLE);
		selenium.waitForPageToLoad("30000");
		selenium.click("//img[@class='ui-datepicker-trigger']");
		selenium.waitForCondition("selenium.isElementPresent(\"xpath=//*[@id='ui-datepicker-div']/table/tbody/tr/td/a\");", "10000");
//		selenium.click("//table[@class='ui-datepicker-calendar']/tbody/tr/td[@class!='ui-datepicker-today'][1]/a");
		selenium.click("//table[@class='ui-datepicker-calendar']/tbody/tr/td[1]");
		selenium.waitForPageToLoad("10000");
		

		int nbLigneDeCompte = selenium.getXpathCount("//table[@id='operations']/tbody/tr").intValue();
		boolean negatif = false;
		for (int i = 1; i <= nbLigneDeCompte; i++) {
			String s = selenium.getText("//table[@id='operations']/tbody/tr[" + i + "]/td[1]").trim();

			Matcher matcher = pattern.matcher(s);
			if (!matcher.find() || !matcher.group(1).equals(actualMonth)) {
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
