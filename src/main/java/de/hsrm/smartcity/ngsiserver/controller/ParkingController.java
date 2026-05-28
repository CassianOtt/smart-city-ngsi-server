package de.hsrm.smartcity.ngsiserver.controller;

import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

    @GetMapping(value = "/parking", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/parking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getParkingData() {
        try {
            String jsonContent = JsonFileReader.readJsonFile("Parking_Entity_GeoJSON.json");
            return ResponseEntity.ok(jsonContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Konnte Datei nicht lesen\"}");
        }
    }
}