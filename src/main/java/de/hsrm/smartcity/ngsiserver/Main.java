package de.hsrm.smartcity.ngsiserver;

import de.hsrm.smartcity.ngsiserver.service.NgsiClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // Startet den eingebauten Tomcat-Server auf Port 8080
        SpringApplication.run(Main.class, args);
    }

    /**
     * Der CommandLineRunner wird von Spring Boot automatisch ausgeführt,
     * sobald der Server erfolgreich hochgefahren ist.
     */
    @Bean
    public CommandLineRunner run(NgsiClientService clientService) {
        return args -> {
            // Kurze Verzögerung von 2 Sekunden, damit die Server-Endpoints 
            // garantiert bereit sind, bevor der Client sie abfragt
            Thread.sleep(2000);
            
            System.out.println("\n==================================================");
            System.out.println("STREAMS & PROTOKOLLE: Starte NGSI-Client Datenabfrage...");
            System.out.println("==================================================");
            
            clientService.fetchAndGenerateXml();
        };
    }
}