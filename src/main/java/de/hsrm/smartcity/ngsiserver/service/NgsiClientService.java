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
    private final XmlMapper xmlMapper; // Schreibt das XML

    private final String VEHICLE_URL = "http://localhost:8080/vehicle";
    private final String PARKING_URL = "http://localhost:8080/parking";

    public NgsiClientService() {
        this.restClient = RestClient.create();
        this.jsonMapper = new ObjectMapper(); // Normaler Jackson ObjectMapper für JSON
        this.xmlMapper = new XmlMapper();
    }

    public void fetchAndGenerateXml() {
        try {
            System.out.println("Rufe Fahrzeugdaten ab von: " + VEHICLE_URL);
            String vehicleRawJson = restClient.get().uri(VEHICLE_URL).retrieve().body(String.class);

            System.out.println("Rufe Parkplatzdaten ab von: " + PARKING_URL);
            String parkingRawJson = restClient.get().uri(PARKING_URL).retrieve().body(String.class);

            SmartCityData combinedData = parseNgsiJson(vehicleRawJson, parkingRawJson);

            File combinedOutputFile = new File("smartcity_output.xml");
            writeXmlFile(combinedData, combinedOutputFile);

            VehicleData vehicleData = new VehicleData();
            vehicleData.getVehicles().addAll(combinedData.getVehicles());
            File vehicleOutputFile = new File("vehicle_output.xml");
            writeXmlFile(vehicleData, vehicleOutputFile);

            ParkingData parkingData = new ParkingData();
            parkingData.getParkings().addAll(combinedData.getParkings());
            File parkingOutputFile = new File("parking_output.xml");
            writeXmlFile(parkingData, parkingOutputFile);

            System.out.println("=== XML-DATEIEN ERFOLGREICH GENERIERT ===");
            System.out.println("Combined: " + combinedOutputFile.getAbsolutePath());
            System.out.println("Vehicle: " + vehicleOutputFile.getAbsolutePath());
            System.out.println("Parking: " + parkingOutputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben oder Parsen: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Fehler bei der Kommunikation mit dem Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SmartCityData parseNgsiJson(String vehicleRawJson, String parkingRawJson) throws IOException {
        SmartCityData combinedData = new SmartCityData();

        if (vehicleRawJson != null && !vehicleRawJson.isBlank()) {
            JsonNode vehicleJson = jsonMapper.readTree(vehicleRawJson);
            combinedData.getVehicles().add(parseVehicle(vehicleJson));
        }

        if (parkingRawJson != null && !parkingRawJson.isBlank()) {
            JsonNode parkingJson = jsonMapper.readTree(parkingRawJson);
            combinedData.getParkings().add(parseParking(parkingJson));
        }

        return combinedData;
    }

    public void writeXmlFile(Object data, File outputFile) throws IOException {
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, data);
    }

    private VehicleXml parseVehicle(JsonNode vehicleJson) {
        VehicleXml v = new VehicleXml();
        v.setId(vehicleJson.get("id").asText());
        v.setType(vehicleJson.get("type").asText());
        v.setBrandName(vehicleJson.get("brandName").get("value").asText());

        JsonNode languageMap = vehicleJson.get("street").get("languageMap");
        String street = languageMap.has("de") ? languageMap.get("de").asText()
                : languageMap.has("nl") ? languageMap.get("nl").asText()
                        : languageMap.has("fr") ? languageMap.get("fr").asText() : "";
        v.setStreet(street);

        VehicleXml.IsParkedXml isParked = new VehicleXml.IsParkedXml();
        isParked.setParkingId(vehicleJson.get("isParked").get("object").asText());
        isParked.setObservedAt(vehicleJson.get("isParked").get("observedAt").asText());
        isParked.setProvidedBy(vehicleJson.get("isParked").get("providedBy").get("object").asText());
        v.setIsParked(isParked);

        v.setCategory(vehicleJson.get("category").get("vocab").asText());
        return v;
    }

    private ParkingXml parseParking(JsonNode parkingJson) {
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

        p.setTotalSpotNumber(properties.get("total_spot_number") != null ? properties.get("total_spot_number").asInt()
                : properties.get("totalSpotNumber").asInt());
        return p;
    }
}