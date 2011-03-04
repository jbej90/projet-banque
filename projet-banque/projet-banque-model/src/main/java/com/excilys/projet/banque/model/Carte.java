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

@SuppressWarnings("serial")
@Entity
@Table(name = "carte")
public class Carte implements Serializable {

	private Integer id;
	private Compte compte;
	private String numCarte;
	private Date dateLim;
	private TypeCarte type;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Compte.class)
	@JoinColumn(name = "compte_fk")
	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Column(name = "num_carte", nullable = false, unique = true, length = 16)
	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}

	@Column(name = "date_lim", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDateLim() {
		return dateLim;
	}

	public void setDateLim(Date dateLim) {
		this.dateLim = dateLim;
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	public TypeCarte getType() {
		return type;
	}

	public void setType(TypeCarte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Carte [id=" + id + ", compte=" + compte + ", numCarte=" + numCarte + ", dateLim=" + dateLim + ", type=" + type + "]";
	}

}
