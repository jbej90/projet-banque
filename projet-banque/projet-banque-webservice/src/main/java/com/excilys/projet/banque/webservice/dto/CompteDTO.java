package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for compte complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="compte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carte" type="{http://webservice.banque.projet.excilys.com/}carte" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="client" type="{http://webservice.banque.projet.excilys.com/}client" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="libelle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="solde" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compte", propOrder = { "id", "libelle", "solde" })
public class CompteDTO {

	@XmlElement(nillable = true)
	protected int id;
	protected String libelle;
	protected float solde;

	public CompteDTO(int id, String libelle, float solde) {
		this.id = id;
		this.libelle = libelle;
		this.solde = solde;
	}

	public CompteDTO() {
	}

	/**
	 * Gets the value of the id property.
	 * 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 */
	public void setId(int value) {
		this.id = value;
	}

	/**
	 * Gets the value of the libelle property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Sets the value of the libelle property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLibelle(String value) {
		this.libelle = value;
	}

	/**
	 * Gets the value of the solde property.
	 * 
	 */
	public float getSolde() {
		return solde;
	}

	/**
	 * Sets the value of the solde property.
	 * 
	 */
	public void setSolde(float value) {
		this.solde = value;
	}

}
