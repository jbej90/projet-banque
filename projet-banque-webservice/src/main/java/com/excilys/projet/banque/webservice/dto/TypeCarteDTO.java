
package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeCarte.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="typeCarte">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DIFFERE"/>
 *     &lt;enumeration value="IMMEDIAT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeCarte")
@XmlEnum
public enum TypeCarteDTO {

    DIFFERE,
    IMMEDIAT;

    public String value() {
        return name();
    }

    public static TypeCarteDTO fromValue(String v) {
        return valueOf(v);
    }

}
