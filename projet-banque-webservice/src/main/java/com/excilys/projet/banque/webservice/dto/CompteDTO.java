
package com.excilys.projet.banque.webservice.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for compte complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
@XmlType(name = "compte", propOrder = {
    "carte",
    "client",
    "id",
    "libelle",
    "solde"
})
public class CompteDTO {

    @XmlElement(nillable = true)
    protected List<CarteDTO> carte;
    protected ClientDTO client;
    protected int id;
    protected String libelle;
    protected float solde;
    
    public CompteDTO(List<CarteDTO> carte, ClientDTO client, int id,
			String libelle, float solde) {
		this.carte = carte;
		this.client = client;
		this.id = id;
		this.libelle = libelle;
		this.solde = solde;
	}

	public CompteDTO() {
	}

	/**
     * Gets the value of the carte property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carte property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarte().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CarteDTO }
     * 
     * 
     */
    public List<CarteDTO> getCarte() {
        if (carte == null) {
            carte = new ArrayList<CarteDTO>();
        }
        return this.carte;
    }

    /**
     * Gets the value of the client property.
     * 
     * @return
     *     possible object is
     *     {@link ClientDTO }
     *     
     */
    public ClientDTO getClient() {
        return client;
    }

    /**
     * Sets the value of the client property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientDTO }
     *     
     */
    public void setClient(ClientDTO value) {
        this.client = value;
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
