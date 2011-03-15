package com.excilys.projet.banque.client;

import javax.xml.ws.BindingProvider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.banque.webservice.IWService;

public class SoapClient {

	public SoapClient() {
	}

	public static void main(String args[]) {
		String login = "op", password = "op";
		int idCompteSource = 1, idCompteDestination = 2;
		float montant = 10f;
		if (args.length == 5) {
			login = args[0];
			password = args[1];
			idCompteSource = Integer.parseInt(args[2]);
			idCompteDestination = Integer.parseInt(args[3]);
			montant = Float.parseFloat(args[4]);
		}

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/context/applicationContext-Client.xml");
		IWService iwServiceClient = applicationContext.getBean("client", IWService.class);
		BindingProvider provider = (BindingProvider) iwServiceClient;
		provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, login);
		provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
		System.out.println(iwServiceClient.passerOperation(idCompteSource, idCompteDestination, montant));
	}

}
