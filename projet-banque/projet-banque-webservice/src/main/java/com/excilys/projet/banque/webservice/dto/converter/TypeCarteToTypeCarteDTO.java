package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.TypeCarte;
import com.excilys.projet.banque.webservice.dto.TypeCarteDTO;

@Component("typeCarteConverter")
public class TypeCarteToTypeCarteDTO implements Converter<TypeCarte, TypeCarteDTO> {

	@Override
	public TypeCarteDTO convert(TypeCarte source) {
		return TypeCarteDTO.fromValue(source.toString());
	}

	// public static void main(String args[]) {
	// TypeCarteToTypeCarteDTO typeCarteDTO = new TypeCarteToTypeCarteDTO();
	//
	// System.out.println(typeCarteDTO.convert(TypeCarte.IMMEDIAT));
	// }
}
