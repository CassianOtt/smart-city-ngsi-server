package de.hsrm.smartcity.ngsiserver.controller;

import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @GetMapping(value = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVehicleData() {
        try {
            String jsonContent = JsonFileReader.readJsonFile("Vehicle_Example.json");
            return ResponseEntity.ok(jsonContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Konnte Datei nicht lesen\"}");
        }
    }
}