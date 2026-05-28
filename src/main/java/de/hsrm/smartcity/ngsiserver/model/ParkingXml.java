package de.hsrm.smartcity.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO für die XML-Abbildung eines Parkplatzes (Aufgabe 3.2 & 3.3).
 * Entspricht dem <OffStreetParking>-Element im XML-Schema.
 */
@Data
public class ParkingXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    private String name;
    private CoordinatesXml coordinates;
    private AvailableSpotsXml availableSpotNumber;
    private int totalSpotNumber;
}

/**
 * Hilfsklasse für die GeoJSON-Koordinaten [longitude, latitude].
 * Entspricht dem <coordinates>-Element im XML-Schema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class CoordinatesXml {
    private double longitude;
    private double latitude;
}

/**
 * Hilfsklasse für die Detail-Informationen der freien Parkplätze.
 * Entspricht dem <availableSpotNumber>-Element im XML-Schema.
 */
@Data
class AvailableSpotsXml {
    private int value;
    private String observedAt;
    private double reliability;
    private String providedBy;
}