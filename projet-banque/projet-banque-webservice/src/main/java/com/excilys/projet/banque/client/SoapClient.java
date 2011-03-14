package com.excilys.projet.banque.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.webservice.IWService;

public class SoapClient {

	public SoapClient() {
	}

	public static void main(String args[]) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/context/applicationContext-Client.xml");
		IWService iwServiceClient = applicationContext.getBean("client", IWService.class);
		iwServiceClient.passerOperation(1, 2, 20);
	}

}
