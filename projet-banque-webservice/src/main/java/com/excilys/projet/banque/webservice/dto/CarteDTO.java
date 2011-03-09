
package com.excilys.projet.banque.webservice.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for carte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="carte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="compte" type="{http://webservice.banque.projet.excilys.com/}compte" minOccurs="0"/>
 *         &lt;element name="dateLim" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numCarte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://webservice.banque.projet.excilys.com/}typeCarte" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "carte", propOrder = {
    "compte",
    "dateLim",
    "id",
    "numCarte",
    "type"
})
public class CarteDTO {

    protected CompteDTO compte;
    @XmlSchemaType(name = "dateTime")
    protected Date dateLim;
    protected Integer id;
    protected String numCarte;
    protected TypeCarteDTO type;

    public CarteDTO() {
	}

	public CarteDTO(CompteDTO compte, Date dateLim, Integer id,
			String numCarte, TypeCarteDTO type) {
		this.compte = compte;
		this.dateLim = dateLim;
		this.id = id;
		this.numCarte = numCarte;
		this.type = type;
	}

	/**
     * Gets the value of the compte property.
     * 
     * @return
     *     possible object is
     *     {@link CompteDTO }
     *     
     */
    public CompteDTO getCompte() {
        return compte;
    }

    /**
     * Sets the value of the compte property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompteDTO }
     *     
     */
    public void setCompte(CompteDTO value) {
        this.compte = value;
    }

    /**
     * Gets the value of the dateLim property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getDateLim() {
        return dateLim;
    }

    /**
     * Sets the value of the dateLim property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateLim(Date value) {
        this.dateLim = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the numCarte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCarte() {
        return numCarte;
    }

    /**
     * Sets the value of the numCarte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCarte(String value) {
        this.numCarte = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TypeCarteDTO }
     *     
     */
    public TypeCarteDTO getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCarteDTO }
     *     
     */
    public void setType(TypeCarteDTO value) {
        this.type = value;
    }

}
