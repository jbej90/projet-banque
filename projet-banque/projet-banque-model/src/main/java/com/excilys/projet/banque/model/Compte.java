package com.excilys.projet.banque.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "compte")
public class Compte implements Comparable<Compte>, Serializable {
	private int id;
	private String libelle;
	private Client client;
	private float solde;
	private float soldePrevisionnel;
	private Set<Carte> carte;

	public Compte() {
	}

	public Compte(int id, String libelle, Client client, float solde) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.client = client;
		this.solde = solde;
	}

	@Override
	public int compareTo(Compte o) {
		return libelle.compareTo(o.getLibelle());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "libelle", nullable = false, length = 100)
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_fk")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Column(name = "solde_courant", precision = 10, scale = 5)
	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}

	@Transient
	public float getSoldePrevisionnel() {
		return soldePrevisionnel;
	}

	public void setSoldePrevisionnel(float soldePrevisionnel) {
		this.soldePrevisionnel = soldePrevisionnel;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "compte_fk")
	public Set<Carte> getCarte() {
		return carte;
	}

	public void setCarte(Set<Carte> carte) {
		this.carte = carte;
	}

}
