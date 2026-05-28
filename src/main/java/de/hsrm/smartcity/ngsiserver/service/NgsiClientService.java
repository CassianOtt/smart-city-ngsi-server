package de.hsrm.smartcity.ngsiserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.hsrm.smartcity.ngsiserver.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;

/**
 * Service zur Verarbeitung der NGSI-Daten (Aufgabe 3.3)[cite: 18].
 * Fragt die JSON-Endpunkte ab, transformiert die Daten und generiert die XML-Datei[cite: 14].
 */
@Service
public class NgsiClientService {

    private final RestClient restClient;
    private final XmlMapper xmlMapper;

    // Die URLs deines Servers
    private final String VEHICLE_URL = "http://localhost:8080/vehicles";
    private final String PARKING_URL = "http://localhost:8080/parking";

    public NgsiClientService() {
        this.restClient = RestClient.create();
        this.xmlMapper = new XmlMapper();
    }

    /**
     * Holt die JSON-Daten von den Endpunkten und schreibt sie als XML-Datei[cite: 13, 14].
     */
    public void fetchAndGenerateXml() {
        try {
            SmartCityData combinedData = new SmartCityData();

            // 1. Endpoint: Vehicle abfragen & parsen [cite: 13]
            System.out.println("Rufe Fahrzeugdaten ab von: " + VEHICLE_URL);
            JsonNode vehicleJson = restClient.get().uri(VEHICLE_URL).retrieve().body(JsonNode.class);
            
            if (vehicleJson != null) {
                VehicleXml v = new VehicleXml();
                v.setId(vehicleJson.get("id").asText());
                v.setType(vehicleJson.get("type").asText());
                v.setBrandName(vehicleJson.get("brandName").get("value").asText());
                
                // Sprache aus der languageMap auslesen
                JsonNode languageMap = vehicleJson.get("street").get("languageMap");
                String street = languageMap.has("de") ? languageMap.get("de").asText() :
                               languageMap.has("nl") ? languageMap.get("nl").asText() : 
                               languageMap.get("fr").asText();
                v.setStreet(street);
                
                // Zugriff auf die statische innere Klasse von VehicleXml
                VehicleXml.IsParkedXml isParked = new VehicleXml.IsParkedXml();
                isParked.setParkingId(vehicleJson.get("isParked").get("object").asText());
                isParked.setObservedAt(vehicleJson.get("isParked").get("observedAt").asText());
                isParked.setProvidedBy(vehicleJson.get("isParked").get("providedBy").get("object").asText());
                v.setIsParked(isParked);
                
                v.setCategory(vehicleJson.get("category").get("vocab").asText());
                combinedData.getVehicles().add(v);
            }

            // 2. Endpoint: Parking abfragen & parsen [cite: 13]
            System.out.println("Rufe Parkplatzdaten ab von: " + PARKING_URL);
            JsonNode parkingJson = restClient.get().uri(PARKING_URL).retrieve().body(JsonNode.class);
            
            if (parkingJson != null) {
                ParkingXml p = new ParkingXml();
                p.setId(parkingJson.get("id").asText());
                
                JsonNode properties = parkingJson.get("properties");
                p.setType(properties.get("type").asText());
                p.setName(properties.get("name").asText());
                
                // Zugriff auf die statische innere Klasse ParkingXml.CoordinatesXml
                JsonNode coords = parkingJson.get("geometry").get("coordinates");
                p.setCoordinates(new ParkingXml.CoordinatesXml(coords.get(0).asDouble(), coords.get(1).asDouble()));
                
                // Zugriff auf die statische innere Klasse ParkingXml.AvailableSpotsXml
                JsonNode spots = properties.get("availableSpotNumber");
                ParkingXml.AvailableSpotsXml spotsXml = new ParkingXml.AvailableSpotsXml();
                spotsXml.setValue(spots.get("value").asInt());
                spotsXml.setObservedAt(spots.get("observedAt").asText());
                spotsXml.setReliability(spots.get("reliability").asDouble());
                spotsXml.setProvidedBy(spots.get("providedBy").get("object").asText());
                p.setAvailableSpotNumber(spotsXml);
                
                p.setTotalSpotNumber(properties.get("totalSpotNumber").asInt());
                combinedData.getParkings().add(p);
            }

            // 3. Generierung der XML-Datei [cite: 14]
            File outputFile = new File("smartcity_output.xml");
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, combinedData);
            System.out.println("=== XML-DATEI ERFOLGREICH GENERIERT ===");
            System.out.println("Pfad: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der XML: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Fehler bei der Kommunikation mit dem Server: " + e.getMessage());
        }
    }
}