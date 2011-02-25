package com.excilys.projet.banque.model;

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

@Entity
@Table(name="operation")
public class Operation {
	private int id;
	private Type type;
	private Compte compte;
	private Carte carte;
	private String libelle;
	private EtatOperation etat;
	private float montant;
	private Date dateOp;

	public int getId() {
		return id;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public void setId(int id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	//TODO Vérifier que @Column sert à qqch
	@Column(name="type_fk", unique=false, nullable=false)
	@ManyToOne(targetEntity=Type.class)
	@JoinColumn(name="type_fk")
	public void setType(Type type) {
		this.type = type;
	}

	public String getLibelle() {
		return libelle;
	}

	@Column(name="libelle", unique=false, nullable=false)
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public EtatOperation getEtat() {
		return etat;
	}

	@Column(name="etat", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	public void setEtat(EtatOperation etat) {
		this.etat = etat;
	}

	public float getMontant() {
		return montant;
	}

	@Column(name="montant", unique=false, nullable=false)
	public void setMontant(float montant) {
		this.montant = montant;
	}

	public Date getDateOp() {
		return dateOp;
	}

	@Column(name="date_op", unique=false, nullable=false)
	@Temporal(TemporalType.TIME)
	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	//TODO Vérifier que @Column sert à qqch
	@Column(name="compte_fk", unique=false, nullable=false)
	@ManyToOne(targetEntity=Compte.class)
	@JoinColumn(name="compte_fk")
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Compte getCompte() {
		return compte;
	}

	//TODO Vérifier que @Column sert à qqch
	@Column(name="carte_fk", unique=false, nullable=false)
	@ManyToOne(targetEntity=Carte.class)
	@JoinColumn(name="carte_fk")
	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public Carte getCarte() {
		return carte;
	}

}
