package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.EtatOperation;
import com.excilys.projet.banque.webservice.dto.EtatOperationDTO;

@Component("etatOperationConverter")
public class EtatOperationToEtatOperationDTO implements Converter<EtatOperation, EtatOperationDTO> {

	@Override
	public EtatOperationDTO convert(EtatOperation source) {
		return EtatOperationDTO.fromValue(source.toString());
	}

}
