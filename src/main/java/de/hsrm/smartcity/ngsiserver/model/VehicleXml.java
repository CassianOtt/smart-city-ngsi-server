package de.hsrm.smartcity.ngsiserver.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

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

    /**
     * Statische innere Klasse -> Braucht keine eigene Datei und ist trotzdem public!
     */
    @Data
    public static class IsParkedXml {
        private String parkingId;
        private String observedAt;
        private String providedBy;
    }
}