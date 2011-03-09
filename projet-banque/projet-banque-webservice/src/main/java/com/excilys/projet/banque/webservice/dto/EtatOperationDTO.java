
package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for etatOperation.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="etatOperation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EN_COURS"/>
 *     &lt;enumeration value="EFFECTUE"/>
 *     &lt;enumeration value="REFUSE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "etatOperation")
@XmlEnum
public enum EtatOperationDTO {

    EN_COURS,
    EFFECTUE,
    REFUSE;

    public String value() {
        return name();
    }

    public static EtatOperationDTO fromValue(String v) {
        return valueOf(v);
    }

}
