package de.hsrm.smartcity.ngsiserver.controller;

import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

    @RequestMapping(value = "/parking", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getParkingData() {
        try {
            String jsonContent = JsonFileReader.readJsonFile("Parking_Entity_GeoJSON.json");
            return ResponseEntity.ok(jsonContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Konnte Datei nicht lesen\"}");
        }
    }
}