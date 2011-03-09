package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.webservice.dto.ClientDTO;

@Component("clientConverter")
public class ClientToClientDTO implements Converter<Client, ClientDTO>{

	@Override
	public ClientDTO convert(Client source) {
		return null;
	}
	
}
