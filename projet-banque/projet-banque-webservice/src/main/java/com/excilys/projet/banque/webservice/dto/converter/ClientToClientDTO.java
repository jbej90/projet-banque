package com.excilys.projet.banque.webservice.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.excilys.projet.banque.model.Client;
import com.excilys.projet.banque.model.Compte;
import com.excilys.projet.banque.webservice.dto.ClientDTO;
import com.excilys.projet.banque.webservice.dto.CompteDTO;

@Component("clientConverter")
public class ClientToClientDTO implements Converter<Client, ClientDTO> {

	@Autowired
	private CompteToCompteDTO compteConverter;

	@Autowired
	private AuthToAuthDTO authConverter;

	@Override
	public ClientDTO convert(Client source) {
		List<CompteDTO> listComptes = new ArrayList<CompteDTO>();
		for (Compte compte : source.getComptes()) {
			listComptes.add(compteConverter.convert(compte));
		}
		return new ClientDTO(source.getAdresse(), authConverter.convert(source.getAuth()), listComptes, source.getDateLastConnection(), source.getId(), source.getNom(),
				source.getPrenom());
	}

	public void setCompteConverter(CompteToCompteDTO compteConverter) {
		this.compteConverter = compteConverter;
	}

	public void setAuthConverter(AuthToAuthDTO authConverter) {
		this.authConverter = authConverter;
	}

}
