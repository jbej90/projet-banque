package com.excilys.projet.banque.webservice.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for client complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="client">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adresse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auth" type="{http://webservice.banque.projet.excilys.com/}auth" minOccurs="0"/>
 *         &lt;element name="comptes" type="{http://webservice.banque.projet.excilys.com/}compte" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dateLastConnection" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "client", propOrder = { "adresse", "auth", "comptes", "dateLastConnection", "id", "nom", "prenom" })
public class ClientDTO {

	protected String adresse;
	protected AuthDTO auth;
	@XmlElement(nillable = true)
	protected List<CompteDTO> comptes;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateLastConnection;
	protected Integer id;
	protected String nom;
	protected String prenom;

	/**
	 * Gets the value of the adresse property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Sets the value of the adresse property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAdresse(String value) {
		this.adresse = value;
	}

	/**
	 * Gets the value of the auth property.
	 * 
	 * @return possible object is {@link AuthDTO }
	 * 
	 */
	public AuthDTO getAuth() {
		return auth;
	}

	/**
	 * Sets the value of the auth property.
	 * 
	 * @param value
	 *            allowed object is {@link AuthDTO }
	 * 
	 */
	public void setAuth(AuthDTO value) {
		this.auth = value;
	}

	/**
	 * Gets the value of the comptes property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the comptes property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getComptes().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link CompteDTO }
	 * 
	 * 
	 */
	public List<CompteDTO> getComptes() {
		if (comptes == null) {
			comptes = new ArrayList<CompteDTO>();
		}
		return this.comptes;
	}

	/**
	 * Gets the value of the dateLastConnection property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateLastConnection() {
		return dateLastConnection;
	}

	/**
	 * Sets the value of the dateLastConnection property.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateLastConnection(XMLGregorianCalendar value) {
		this.dateLastConnection = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setId(Integer value) {
		this.id = value;
	}

	/**
	 * Gets the value of the nom property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the value of the nom property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNom(String value) {
		this.nom = value;
	}

	/**
	 * Gets the value of the prenom property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Sets the value of the prenom property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPrenom(String value) {
		this.prenom = value;
	}

}
