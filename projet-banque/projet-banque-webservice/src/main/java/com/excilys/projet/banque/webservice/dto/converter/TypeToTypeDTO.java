package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Type;
import com.excilys.projet.banque.webservice.dto.TypeDTO;

@Component("typeConverter")
public class TypeToTypeDTO implements Converter<Type, TypeDTO> {


	@Override
	public TypeDTO convert(Type source) {
		return null;
	}

}
