package com.excilys.projet.banque.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "operation")
public class Operation implements Serializable {
	private Integer id;
	private Type type;
	private Compte compte;
	private Carte carte;
	private String libelle;
	private EtatOperation etat;
	private float montant;
	private Date dateOp;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "libelle", unique = false, nullable = false, length = 30)
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Column(name = "etat")
	@NotNull
	@Enumerated(EnumType.STRING)
	public EtatOperation getEtat() {
		return etat;
	}

	public void setEtat(EtatOperation etat) {
		this.etat = etat;
	}

	@Column(name = "montant")
	@NotNull
	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_op")
	public Date getDateOp() {
		return dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@ManyToOne(targetEntity = Compte.class)
	@JoinColumn(name = "compte_fk")
	public Compte getCompte() {
		return compte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	@ManyToOne(targetEntity = Carte.class, optional = true)
	@JoinColumn(name = "carte_fk")
	public Carte getCarte() {
		return carte;
	}

	@Column(name = "type")
	@NotNull
	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
