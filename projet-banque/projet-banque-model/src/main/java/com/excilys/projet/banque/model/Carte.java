package com.excilys.projet.banque.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="carte")
public class Carte {
	private int id;
	private Compte compte;
	private String numCarte;
	private Date dateLim;
	private TypeCarte type;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, unique=true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="compte_fk")
	@OneToOne(targetEntity=Compte.class)
	public Compte getCompte() {
		return compte;
	}
	
	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Column(name="num_carte", nullable=false, unique=true, length=16)
	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}

	@Column(name="date_lim", nullable=false)
	@Temporal(TemporalType.DATE)
	public Date getDateLim() {
		return dateLim;
	}

	public void setDateLim(Date dateLim) {
		this.dateLim = dateLim;
	}

	@Enumerated(EnumType.STRING)
	public TypeCarte getType() {
		return type;
	}

	public void setType(TypeCarte type) {
		this.type = type;
	}

}
