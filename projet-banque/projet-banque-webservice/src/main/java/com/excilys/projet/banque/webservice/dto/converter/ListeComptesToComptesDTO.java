package com.excilys.projet.banque.webservice.dto.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.webservice.dto.CompteDTO;
import com.excilys.projet.banque.webservice.dto.ComptesDTO;


@Component("listeComptesConverter")
public class ListeComptesToComptesDTO implements Converter<List<Compte>, ComptesDTO>{
	
	@Autowired
	private CompteToCompteDTO compteConverter;

	@Override
	public ComptesDTO convert(List<Compte> source) {
		ComptesDTO comptes = new ComptesDTO();
		for (Compte compte : source) {
			comptes.addCompte(compteConverter.convert(compte));
		}
		return comptes;
	}
	
	public void setCompteConverter(CompteToCompteDTO compteConverter) {
		this.compteConverter = compteConverter;
	}
}
