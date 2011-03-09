package com.excilys.projet.banque.webservice.dto.converter;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Carte;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.webservice.dto.CarteDTO;
import com.excilys.projet.banque.webservice.dto.CompteDTO;

@Component("compteConverter")
public class CompteToCompteDTO implements Converter<Compte, CompteDTO> {

	@Override
	public CompteDTO convert(Compte source) {
		ArrayList<CarteDTO> cartes = new ArrayList<CarteDTO>();
		for (Carte carte : source.getCarte()) {
			CarteToCarteDTO carteConverter = new CarteToCarteDTO();
			cartes.add(carteConverter.convert(carte));
		}
		ClientToClientDTO clientConverter = new ClientToClientDTO();
		return new CompteDTO(cartes, clientConverter.convert(source.getClient()), source.getId(), source.getLibelle(), source.getSolde());
	}

}
