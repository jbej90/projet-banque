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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "client")
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
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nom", length = 30)
	@NotNull
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "prenom", length = 40)
	@NotNull
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "adresse", length = 100)
	@NotNull
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_last_con")
	@NotNull
	public Date getDateLastConnection() {
		return dateLastConnection;
	}

	public void setDateLastConnection(Date dateLastConnection) {
		this.dateLastConnection = dateLastConnection;
	}

	@OneToMany
	@JoinColumn(name = "client_fk")
	@OrderBy("libelle")
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

	public Compte getCompte(int id) {
		for (Compte compte : comptes) {
			if (compte.getId() == id) {
				return compte;
			}
		}
		return null;
	}
}