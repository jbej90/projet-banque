
package com.excilys.projet.banque.webservice.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DEPOT"/>
 *     &lt;enumeration value="RETRAIT"/>
 *     &lt;enumeration value="VIREMENT_INT"/>
 *     &lt;enumeration value="VIREMENT_EXT"/>
 *     &lt;enumeration value="OP_CARTE_IMM"/>
 *     &lt;enumeration value="OP_CARTE_DIFF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "type")
@XmlEnum
public enum TypeDTO {

    DEPOT,
    RETRAIT,
    VIREMENT_INT,
    VIREMENT_EXT,
    OP_CARTE_IMM,
    OP_CARTE_DIFF;

    public String value() {
        return name();
    }

    public static TypeDTO fromValue(String v) {
        return valueOf(v);
    }

}
