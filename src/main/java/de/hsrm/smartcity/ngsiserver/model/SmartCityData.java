package de.hsrm.smartcity.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Haupt-DTO für die XML-Generierung (Aufgabe 3.2 & 3.3).
 * Repräsentiert das Root-Element <SmartCityData> im XML.
 */
@Data
@JacksonXmlRootElement(localName = "SmartCityData")
public class SmartCityData {

    @JacksonXmlProperty(localName = "Vehicle")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<VehicleXml> vehicles = new ArrayList<>();

    @JacksonXmlProperty(localName = "OffStreetParking")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ParkingXml> parkings = new ArrayList<>();
}