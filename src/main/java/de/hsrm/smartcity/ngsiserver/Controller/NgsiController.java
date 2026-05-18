package de.hsrm.smartcity.ngsiserver.controller;

import de.hsrm.smartcity.ngsiserver.service.NgsiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NgsiController {

    private final NgsiService ngsiService;

    public NgsiController(NgsiService ngsiService) {
        this.ngsiService = ngsiService;
    }

    @GetMapping(value = "/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getVehicle() {
        return ResponseEntity.ok(ngsiService.getVehicleData());
    }

    @PostMapping(value = "/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postVehicle() {
        return ResponseEntity.ok(ngsiService.getVehicleData());
    }

    @GetMapping(value = "/parking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getParking() {
        return ResponseEntity.ok(ngsiService.getParkingData());
    }

    @PostMapping(value = "/parking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postParking() {
        return ResponseEntity.ok(ngsiService.getParkingData());
    }
}