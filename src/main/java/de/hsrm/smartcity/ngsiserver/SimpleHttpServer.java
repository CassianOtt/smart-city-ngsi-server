package de.hsrm.smartcity.ngsiserver;

import com.sun.net.httpserver.HttpServer;
import de.hsrm.smartcity.ngsiserver.controller.VehicleHandler;
import de.hsrm.smartcity.ngsiserver.controller.ParkingHandler;

import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public void start() {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/vehicle", new VehicleHandler());
            server.createContext("/parking", new ParkingHandler());

            server.setExecutor(null);
            server.start();

            System.out.println("Server läuft auf Port 8080");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}