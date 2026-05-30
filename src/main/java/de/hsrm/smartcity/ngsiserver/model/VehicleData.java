package de.hsrm.smartcity.ngsiserver.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "VehicleData")
public class VehicleData {

    @JacksonXmlProperty(localName = "Vehicle")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<VehicleXml> vehicles = new ArrayList<>();
}
