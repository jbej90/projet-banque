
package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for operation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carte" type="{http://webservice.banque.projet.excilys.com/}carte" minOccurs="0"/>
 *         &lt;element name="compte" type="{http://webservice.banque.projet.excilys.com/}compte" minOccurs="0"/>
 *         &lt;element name="dateOp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="etat" type="{http://webservice.banque.projet.excilys.com/}etatOperation" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="libelle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montant" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="type" type="{http://webservice.banque.projet.excilys.com/}type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operation", propOrder = {
    "carte",
    "compte",
    "dateOp",
    "etat",
    "id",
    "libelle",
    "montant",
    "type"
})
public class OperationDTO {

    protected CarteDTO carte;
    protected CompteDTO compte;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOp;
    protected EtatOperationDTO etat;
    protected Integer id;
    protected String libelle;
    protected float montant;
    protected TypeDTO type;

    /**
     * Gets the value of the carte property.
     * 
     * @return
     *     possible object is
     *     {@link CarteDTO }
     *     
     */
    public CarteDTO getCarte() {
        return carte;
    }

    /**
     * Sets the value of the carte property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarteDTO }
     *     
     */
    public void setCarte(CarteDTO value) {
        this.carte = value;
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
     * Gets the value of the dateOp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOp() {
        return dateOp;
    }

    /**
     * Sets the value of the dateOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOp(XMLGregorianCalendar value) {
        this.dateOp = value;
    }

    /**
     * Gets the value of the etat property.
     * 
     * @return
     *     possible object is
     *     {@link EtatOperationDTO }
     *     
     */
    public EtatOperationDTO getEtat() {
        return etat;
    }

    /**
     * Sets the value of the etat property.
     * 
     * @param value
     *     allowed object is
     *     {@link EtatOperationDTO }
     *     
     */
    public void setEtat(EtatOperationDTO value) {
        this.etat = value;
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
     * Gets the value of the libelle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Sets the value of the libelle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibelle(String value) {
        this.libelle = value;
    }

    /**
     * Gets the value of the montant property.
     * 
     */
    public float getMontant() {
        return montant;
    }

    /**
     * Sets the value of the montant property.
     * 
     */
    public void setMontant(float value) {
        this.montant = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link TypeDTO }
     *     
     */
    public TypeDTO getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeDTO }
     *     
     */
    public void setType(TypeDTO value) {
        this.type = value;
    }

}
