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
public class Operation implements Serializable, Comparable<Operation> {
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_op", nullable = false)
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

	@Override
	public int compareTo(Operation o) {
		return getDateOp().compareTo(o.getDateOp());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carte == null) ? 0 : carte.hashCode());
		result = prime * result + ((compte == null) ? 0 : compte.hashCode());
		result = prime * result + ((dateOp == null) ? 0 : dateOp.hashCode());
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + Float.floatToIntBits(montant);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (carte == null) {
			if (other.carte != null)
				return false;
		} else if (!carte.equals(other.carte))
			return false;
		if (compte == null) {
			if (other.compte != null)
				return false;
		} else if (!compte.equals(other.compte))
			return false;
		if (dateOp == null) {
			if (other.dateOp != null)
				return false;
		} else if (!dateOp.equals(other.dateOp))
			return false;
		if (etat != other.etat)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (Float.floatToIntBits(montant) != Float.floatToIntBits(other.montant))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
