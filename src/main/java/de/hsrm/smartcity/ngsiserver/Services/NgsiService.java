package de.hsrm.smartcity.ngsiserver.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class NgsiService {

    public String getVehicleData() {
        return readFileFromResources("vehicle.json");
    }

    public String getParkingData() {
        return readFileFromResources("parking.json");
    }

    private String readFileFromResources(String fileName) {
        try {
            var inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (inputStream == null) {
                return "{ \"error\": \"Datei nicht gefunden: " + fileName + "\" }";
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            return "{ \"error\": \"Fehler beim Lesen der Datei\" }";
        }
    }
}