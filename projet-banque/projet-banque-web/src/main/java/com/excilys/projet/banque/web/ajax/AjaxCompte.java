package com.excilys.projet.banque.web.ajax;

/**
 * <p>
 * POJO utilisé uniquement pour la sérialisation en JSON.
 * </p>
 * <p>
 * Cette classe est utilisée pour les méthodes AJAX et ne doit donc envoyer que les attributs nécessaires
 * </p>
 * <p>
 * Elle contient les informations d'un compte
 * </p>
 * 
 * @author excilys
 * 
 */
public class AjaxCompte implements Comparable<AjaxCompte> {

	private int		id;
	private String	libelle;
	private float	solde;

	public AjaxCompte() {
	}

	public AjaxCompte(int id, String libelle, float solde) {
		this.id = id;
		this.libelle = libelle;
		this.solde = solde;
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

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}

	@Override
	public int compareTo(AjaxCompte o) {
		return getLibelle().compareTo(o.getLibelle());
	}
}