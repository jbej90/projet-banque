package com.excilys.projet.banque.dao.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TypeDAOImplTest {

	private static TypeDAOImpl typeDAOImpl;

	@BeforeClass
	public static void setUp() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("/context/applicationContext-dao-impl-hibernate.xml");
		typeDAOImpl = appContext.getBean("typeDao", TypeDAOImpl.class);
	}

	@Test
	public void saveTest() {
		
	}

	@Test
	public void findAllTest() {
		
	}
	
	@Test
	public void findByIdTest() {
		
	}
}
