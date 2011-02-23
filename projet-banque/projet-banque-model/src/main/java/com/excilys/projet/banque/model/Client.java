package com.excilys.projet.banque.model;

import java.util.Date;
import java.util.Set;

public class Client {
	private Integer id;
	private String nom;
	private String prenom;
	private String adresse;
	private Date dateLastConnection;
	private Set<Compte> comptes;
	private float totalSoldeComptes = 0;

	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", dateLastConnection=" + dateLastConnection + ", comptes=" + comptes + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDateLastConnection() {
		return dateLastConnection;
	}

	public void setDateLastConnection(Date dateLastConnection) {
		this.dateLastConnection = dateLastConnection;
	}

	public Set<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Set<Compte> comptes) {
		this.comptes = comptes;
	}

	public float getTotalSoldeComptes() {
		return totalSoldeComptes;
	}
	
	public void setTotalSoldeComptes(float totalSoldeCompte) {
		this.totalSoldeComptes = totalSoldeCompte;
	}
}