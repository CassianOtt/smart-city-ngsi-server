package de.hsrm.smartcity.ngsiserver.controller;

import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @RequestMapping(value = { "/vehicle", "/vehicles" }, method = { RequestMethod.GET,
            RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVehicleData() {
        try {
            String jsonContent = JsonFileReader.readJsonFile("Vehicle_Example.json");
            return ResponseEntity.ok(jsonContent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Konnte Datei nicht lesen\"}");
        }
    }
}