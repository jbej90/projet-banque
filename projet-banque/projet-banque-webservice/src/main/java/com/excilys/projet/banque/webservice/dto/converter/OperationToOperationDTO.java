package com.excilys.projet.banque.webservice.dto.converter;

//import org.joda.time.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Operation;
import com.excilys.projet.banque.webservice.dto.OperationDTO;

@Component("operationConverter")
public class OperationToOperationDTO implements Converter<Operation, OperationDTO> {

	@Autowired
	private CarteToCarteDTO carteConverter;

	@Autowired
	private CompteToCompteDTO compteConverter;

	@Autowired
	private EtatOperationToEtatOperationDTO etatOperationConverter;

	@Autowired
	private TypeToTypeDTO typeConverter;

	@Override
	public OperationDTO convert(Operation source) {
		return new OperationDTO(carteConverter.convert(source.getCarte()), compteConverter.convert(source.getCompte()), source.getDateOp(), etatOperationConverter.convert(source
				.getEtat()), source.getId(), source.getLibelle(), source.getMontant(), typeConverter.convert(source.getType()));
	}

	public void setCarteConverter(CarteToCarteDTO carteConverter) {
		this.carteConverter = carteConverter;
	}

	public void setCompteConverter(CompteToCompteDTO compteConverter) {
		this.compteConverter = compteConverter;
	}

}
