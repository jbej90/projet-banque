package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;

import com.excilys.projet.banque.model.TypeCarte;
import com.excilys.projet.banque.webservice.dto.TypeCarteDTO;

public class TypeCarteToTypeCarteDTO implements Converter<TypeCarte, TypeCarteDTO> {

	@Override
	public TypeCarteDTO convert(TypeCarte source) {
		return null;
	}

}
