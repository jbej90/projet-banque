package com.excilys.projet.banque.web.ajax;

import java.util.Date;

import com.excilys.projet.banque.model.Type;

/**
 * <p>
 * POJO utilisé uniquement pour la sérialisation en JSON.
 * </p>
 * <p>
 * Cette classe est utilisée pour les méthodes AJAX et ne doit donc envoyer que les attributs nécessaires
 * </p>
 * <p>
 * Elle contient les informations d'une opération
 * </p>
 * 
 * @author excilys
 *
 */
public class AjaxOperation implements Comparable<AjaxOperation> {

	private Date	date;
	private String	libelle;
	private Type	type;
	private float	montant;

	public AjaxOperation() {
	}

	public AjaxOperation(Date date, String libelle, Type type, float montant) {
		super();
		this.date = date;
		this.libelle = libelle;
		this.type = type;
		this.montant = montant;
	}

	@Override
	public int compareTo(AjaxOperation o) {
		return getDate().compareTo(o.getDate());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}
}
