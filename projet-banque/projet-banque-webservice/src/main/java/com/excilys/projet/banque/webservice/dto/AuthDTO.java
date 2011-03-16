package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for auth complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="auth">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="client" type="{http://webservice.banque.projet.excilys.com/}client" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auth", propOrder = { "client", "enabled", "id", "login", "password" })
public class AuthDTO {

	protected ClientDTO client;
	protected int enabled;
	protected Integer id;
	protected String login;
	protected String password;

	public AuthDTO() {
	}

	/**
	 * Constructeur
	 * 
	 * @param client
	 * @param enabled
	 * @param id
	 * @param login
	 * @param password
	 */

	public AuthDTO(ClientDTO client, int enabled, Integer id, String login, String password) {
		this.client = client;
		this.enabled = enabled;
		this.id = id;
		this.login = login;
		this.password = password;
	}

	/**
	 * Gets the value of the client property.
	 * 
	 * @return possible object is {@link ClientDTO }
	 * 
	 */
	public ClientDTO getClient() {
		return client;
	}

	/**
	 * Sets the value of the client property.
	 * 
	 * @param value
	 *            allowed object is {@link ClientDTO }
	 * 
	 */
	public void setClient(ClientDTO value) {
		this.client = value;
	}

	/**
	 * Gets the value of the enabled property.
	 * 
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * Sets the value of the enabled property.
	 * 
	 */
	public void setEnabled(int value) {
		this.enabled = value;
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
	 * Gets the value of the login property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the value of the login property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLogin(String value) {
		this.login = value;
	}

	/**
	 * Gets the value of the password property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the value of the password property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPassword(String value) {
		this.password = value;
	}

}
