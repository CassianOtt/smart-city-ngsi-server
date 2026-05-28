package de.hsrm.smartcity.ngsiserver.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /**
     * Statische innere Klasse für Koordinaten
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinatesXml {
        private double longitude;
        private double latitude;
    }

    /**
     * Statische innere Klasse für freie Plätze
     */
    @Data
    public static class AvailableSpotsXml {
        private int value;
        private String observedAt;
        private double reliability;
        private String providedBy;
    }
}