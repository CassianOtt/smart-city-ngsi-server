package de.hsrm.smartcity.ngsiserver.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ParkingData")
public class ParkingData {

    @JacksonXmlProperty(localName = "OffStreetParking")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ParkingXml> parkings = new ArrayList<>();
}
