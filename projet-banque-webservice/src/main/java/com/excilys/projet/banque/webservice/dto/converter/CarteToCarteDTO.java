package com.excilys.projet.banque.webservice.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.webservice.dto.CarteDTO;

@Component("carteConverter")
public class CarteToCarteDTO implements Converter<Carte, CarteDTO> {

	@Autowired
	private CompteToCompteDTO compteConverter;
	@Autowired
	private TypeCarteToTypeCarteDTO typeCarteConverter;

	@Override
	public CarteDTO convert(Carte source) {
		return new CarteDTO(compteConverter.convert(source.getCompte()), source.getDateLim(), source.getId(), source.getNumCarte(), typeCarteConverter.convert(source.getType()));
	}

	public void setCompteConverter(CompteToCompteDTO compteConverter) {
		this.compteConverter = compteConverter;
	}

	public void setTypeCarteConverter(TypeCarteToTypeCarteDTO typeCarteConverter) {
		this.typeCarteConverter = typeCarteConverter;
	}
}
