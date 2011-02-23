package com.excilys.projet.banque.model;

public class Compte implements Comparable<Compte> {
	private int id;
	private String libelle;
	private Client client;
	private float solde;
	
	
	@Override
	public int compareTo(Compte o) {
		return libelle.compareTo(o.getLibelle());
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}

}
