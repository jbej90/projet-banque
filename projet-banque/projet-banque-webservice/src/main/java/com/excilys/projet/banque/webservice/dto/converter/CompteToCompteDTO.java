package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.webservice.dto.CompteDTO;

@Component("compteConverter")
public class CompteToCompteDTO implements Converter<Compte, CompteDTO> {

	@Override
	public CompteDTO convert(Compte source) {
		return new CompteDTO(source.getId(), source.getLibelle(), source.getSolde());
	}

}
