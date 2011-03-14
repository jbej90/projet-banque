package com.excilys.projet.banque.client;

import javax.xml.ws.BindingProvider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.webservice.IWService;

public class SoapClient {

	public SoapClient() {
	}

	public static void main(String args[]) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/context/applicationContext-Client.xml");
		IWService iwServiceClient = applicationContext.getBean("client", IWService.class);
		BindingProvider provider = (BindingProvider) iwServiceClient;
		provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "login");
		provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "pass");
		// iwServiceClient.passerOperation(1, 2, 20);
	}

}
