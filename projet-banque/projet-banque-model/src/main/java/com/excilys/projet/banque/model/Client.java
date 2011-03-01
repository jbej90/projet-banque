package com.excilys.projet.banque.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.internal.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

	private Integer id;
	private String nom;
	private String prenom;
	private String adresse;
	private Date dateLastConnection;
	private Set<Compte> comptes;

	private Auth auth;

	// private float totalSoldeComptes = 0;

	public Client() {
	}

	public Client(Integer id, String nom, String prenom, String adresse, Date dateLastConnection, Set<Compte> comptes) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.dateLastConnection = dateLastConnection;
		this.comptes = comptes;
		// this.totalSoldeComptes = totalSoldeComptes;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", dateLastConnection=" + dateLastConnection + ", comptes=" + comptes + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NOM", length = 30)
	@NotNull
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "PRENOM", length = 40)
	@NotNull
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "ADRESSE", length = 100)
	@NotNull
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_LAST_CON")
	@NotNull
	public Date getDateLastConnection() {
		return dateLastConnection;
	}

	public void setDateLastConnection(Date dateLastConnection) {
		this.dateLastConnection = dateLastConnection;
	}

	@OneToMany
	@JoinColumn(name = "CLIENT_FK")
	public Set<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Set<Compte> comptes) {
		this.comptes = comptes;
	}

	@OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy = "client")
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	//
	// public float getTotalSoldeComptes() {
	// return totalSoldeComptes;
	// }
	//
	// public void setTotalSoldeComptes(float totalSoldeCompte) {
	// this.totalSoldeComptes = totalSoldeCompte;
	// }
}