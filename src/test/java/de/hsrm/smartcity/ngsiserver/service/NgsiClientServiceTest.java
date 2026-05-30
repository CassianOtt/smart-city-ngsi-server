package de.hsrm.smartcity.ngsiserver.service;

import de.hsrm.smartcity.ngsiserver.model.ParkingData;
import de.hsrm.smartcity.ngsiserver.model.SmartCityData;
import de.hsrm.smartcity.ngsiserver.model.VehicleData;
import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class NgsiClientServiceTest {

    @Test
    void parseNgsiJsonProducesSmartCityData() throws Exception {
        NgsiClientService service = new NgsiClientService();
        String vehicleJson = JsonFileReader.readJsonFile("Vehicle_Example.json");
        String parkingJson = JsonFileReader.readJsonFile("Parking_Entity_GeoJSON.json");

        SmartCityData data = service.parseNgsiJson(vehicleJson, parkingJson);

        assertThat(data).isNotNull();
        assertThat(data.getVehicles()).hasSize(1);
        assertThat(data.getParkings()).hasSize(1);

        assertThat(data.getVehicles().get(0).getBrandName()).isEqualTo("Mercedes");
        assertThat(data.getVehicles().get(0).getStreet()).isEqualTo("Grote Markt");
        assertThat(data.getVehicles().get(0).getCategory()).isEqualTo("non-commercial");

        assertThat(data.getParkings().get(0).getName()).isEqualTo("Downtown One");
        assertThat(data.getParkings().get(0).getAvailableSpotNumber().getValue()).isEqualTo(121);
    }

    @Test
    void writeXmlFileGeneratesSeparateVehicleAndParkingXml() throws Exception {
        NgsiClientService service = new NgsiClientService();
        String vehicleJson = JsonFileReader.readJsonFile("Vehicle_Example.json");
        String parkingJson = JsonFileReader.readJsonFile("Parking_Entity_GeoJSON.json");

        SmartCityData data = service.parseNgsiJson(vehicleJson, parkingJson);

        VehicleData vehicleData = new VehicleData();
        vehicleData.getVehicles().addAll(data.getVehicles());
        File vehicleOutputFile = File.createTempFile("vehicle-test", ".xml");
        vehicleOutputFile.deleteOnExit();
        service.writeXmlFile(vehicleData, vehicleOutputFile);

        ParkingData parkingData = new ParkingData();
        parkingData.getParkings().addAll(data.getParkings());
        File parkingOutputFile = File.createTempFile("parking-test", ".xml");
        parkingOutputFile.deleteOnExit();
        service.writeXmlFile(parkingData, parkingOutputFile);

        assertThat(vehicleOutputFile).exists();
        String vehicleXml = Files.readString(vehicleOutputFile.toPath(), StandardCharsets.UTF_8);
        assertThat(vehicleXml).contains("<VehicleData>");
        assertThat(vehicleXml).contains("<Vehicle");
        assertThat(vehicleXml).contains("<brandName>Mercedes</brandName>");

        assertThat(parkingOutputFile).exists();
        String parkingXml = Files.readString(parkingOutputFile.toPath(), StandardCharsets.UTF_8);
        assertThat(parkingXml).contains("<ParkingData>");
        assertThat(parkingXml).contains("<OffStreetParking");
        assertThat(parkingXml).contains("<name>Downtown One</name>");
    }
}
