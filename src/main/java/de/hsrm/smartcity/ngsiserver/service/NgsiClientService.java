package de.hsrm.smartcity.ngsiserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; // Wichtig: Für das JSON-Parsing
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrm.smartcity.ngsiserver.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;

@Service
public class NgsiClientService {

    private final RestClient restClient;
    private final ObjectMapper jsonMapper; // Liest das JSON
    private final XmlMapper xmlMapper;     // Schreibt das XML

    private final String VEHICLE_URL = "http://localhost:8080/vehicles";
    private final String PARKING_URL = "http://localhost:8080/parking";

    public NgsiClientService() {
        this.restClient = RestClient.create();
        this.jsonMapper = new ObjectMapper(); // Normaler Jackson ObjectMapper für JSON
        this.xmlMapper = new XmlMapper();
    }

    public void fetchAndGenerateXml() {
        try {
            SmartCityData combinedData = new SmartCityData();

            // 1. Endpoint: Vehicle abfragen (als String) & parsen
            System.out.println("Rufe Fahrzeugdaten ab von: " + VEHICLE_URL);
            String vehicleRawJson = restClient.get().uri(VEHICLE_URL).retrieve().body(String.class);
            
            if (vehicleRawJson != null) {
                JsonNode vehicleJson = jsonMapper.readTree(vehicleRawJson); // Manuelles Parsing zu JsonNode
                
                VehicleXml v = new VehicleXml();
                v.setId(vehicleJson.get("id").asText());
                v.setType(vehicleJson.get("type").asText());
                v.setBrandName(vehicleJson.get("brandName").get("value").asText());
                
                JsonNode languageMap = vehicleJson.get("street").get("languageMap");
                String street = languageMap.has("de") ? languageMap.get("de").asText() :
                               languageMap.has("nl") ? languageMap.get("nl").asText() : 
                               languageMap.get("fr").asText();
                v.setStreet(street);
                
                VehicleXml.IsParkedXml isParked = new VehicleXml.IsParkedXml();
                isParked.setParkingId(vehicleJson.get("isParked").get("object").asText());
                isParked.setObservedAt(vehicleJson.get("isParked").get("observedAt").asText());
                isParked.setProvidedBy(vehicleJson.get("isParked").get("providedBy").get("object").asText());
                v.setIsParked(isParked);
                
                v.setCategory(vehicleJson.get("category").get("vocab").asText());
                combinedData.getVehicles().add(v);
            }

            // 2. Endpoint: Parking abfragen (als String) & parsen
            System.out.println("Rufe Parkplatzdaten ab von: " + PARKING_URL);
            String parkingRawJson = restClient.get().uri(PARKING_URL).retrieve().body(String.class);
            
            if (parkingRawJson != null) {
                JsonNode parkingJson = jsonMapper.readTree(parkingRawJson); // Manuelles Parsing zu JsonNode
                
                ParkingXml p = new ParkingXml();
                p.setId(parkingJson.get("id").asText());
                
                JsonNode properties = parkingJson.get("properties");
                p.setType(properties.get("type").asText());
                p.setName(properties.get("name").asText());
                
                JsonNode coords = parkingJson.get("geometry").get("coordinates");
                p.setCoordinates(new ParkingXml.CoordinatesXml(coords.get(0).asDouble(), coords.get(1).asDouble()));
                
                JsonNode spots = properties.get("availableSpotNumber");
                ParkingXml.AvailableSpotsXml spotsXml = new ParkingXml.AvailableSpotsXml();
                spotsXml.setValue(spots.get("value").asInt());
                spotsXml.setObservedAt(spots.get("observedAt").asText());
                spotsXml.setReliability(spots.get("reliability").asDouble());
                spotsXml.setProvidedBy(spots.get("providedBy").get("object").asText());
                p.setAvailableSpotNumber(spotsXml);
                
                p.setTotalSpotNumber(properties.get("total_spot_number") != null ? 
                        properties.get("total_spot_number").asInt() : properties.get("totalSpotNumber").asInt());
                combinedData.getParkings().add(p);
            }

            // 3. Generierung der XML-Datei
            File outputFile = new File("smartcity_output.xml");
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, combinedData);
            System.out.println("=== XML-DATEI ERFOLGREICH GENERIERT ===");
            System.out.println("Pfad: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben oder Parsen: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Fehler bei der Kommunikation mit dem Server: " + e.getMessage());
            e.printStackTrace(); // Gibt detaillierten Stacktrace aus, falls noch was hakt
        }
    }
}