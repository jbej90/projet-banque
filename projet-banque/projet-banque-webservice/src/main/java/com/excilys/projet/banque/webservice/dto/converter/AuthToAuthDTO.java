package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Auth;
import com.excilys.projet.banque.webservice.dto.AuthDTO;

@Component("authConverter")
public class AuthToAuthDTO implements Converter<Auth, AuthDTO> {

	@Autowired
	private ClientToClientDTO clientConverter;

	@Override
	public AuthDTO convert(Auth source) {
		return new AuthDTO(clientConverter.convert(source.getClient()), source.getEnabled(), source.getId(), source.getLogin(), source.getPassword());
	}

	public void setClientConverter(ClientToClientDTO clientConverter) {
		this.clientConverter = clientConverter;
	}

}
