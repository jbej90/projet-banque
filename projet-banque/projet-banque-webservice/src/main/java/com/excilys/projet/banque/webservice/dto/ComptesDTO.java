package com.excilys.projet.banque.webservice.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comptes", propOrder = { "comptes" })
public class ComptesDTO {
	
	@XmlElementWrapper(name="comptes")
	@XmlElement(name="compte")
	private List<CompteDTO> comptes;

	public ComptesDTO() {
		comptes = new ArrayList<CompteDTO>();
	}

	public List<CompteDTO> getComptes() {
		return comptes;
	}

	public void setComptes(List<CompteDTO> comptes) {
		this.comptes = comptes;
	}
	
	public void addCompte(CompteDTO compte) {
		comptes.add(compte);
	}

	@Override
	public String toString() {
		return "ComptesDTO [comptes=" + comptes + "]";
	}
	
}
