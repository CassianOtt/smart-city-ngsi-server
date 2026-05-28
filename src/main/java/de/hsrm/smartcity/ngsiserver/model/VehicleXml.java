package de.hsrm.smartcity.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * DTO für die XML-Abbildung eines Fahrzeugs (Aufgabe 3.2 & 3.3).
 * Entspricht dem <Vehicle>-Element im XML-Schema.
 */
@Data
public class VehicleXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    private String brandName;
    private String street;
    private IsParkedXml isParked;
    private String category;
}

/**
 * Hilfsklasse für die Parkplatz-Beziehung des Fahrzeugs.
 * Entspricht dem <isParked>-Element im XML-Schema.
 */
@Data
class IsParkedXml {
    private String parkingId;
    private String observedAt;
    private String providedBy;
}