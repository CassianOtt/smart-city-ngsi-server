package de.hsrm.smartcity.ngsiserver;

import com.sun.net.httpserver.HttpServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimpleHttpServer {

    public void start() {

        try {

            HttpServer server = HttpServer.create(
                    new InetSocketAddress(8080), 0);

            server.createContext("/vehicle", exchange -> {

                String method = exchange.getRequestMethod();

                if (!method.equalsIgnoreCase("GET") &&
                        !method.equalsIgnoreCase("POST")) {

                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                String response = readResourceFile("Vehicle_Example.json");

                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(
                        200,
                        response.getBytes(StandardCharsets.UTF_8).length);

                OutputStream output = exchange.getResponseBody();

                output.write(
                        response.getBytes(StandardCharsets.UTF_8));

                output.close();
            });

            server.createContext("/parking", exchange -> {

                String method = exchange.getRequestMethod();

                if (!method.equalsIgnoreCase("GET") &&
                        !method.equalsIgnoreCase("POST")) {

                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                String response = readResourceFile("Parking_Entity_GeoJSON.json");

                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(
                        200,
                        response.getBytes(StandardCharsets.UTF_8).length);

                OutputStream output = exchange.getResponseBody();

                output.write(
                        response.getBytes(StandardCharsets.UTF_8));

                output.close();
            });

            server.setExecutor(null);

            server.start();

            System.out.println("Server läuft auf Port 8080");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readResourceFile(String fileName) {

        try {

            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (inputStream == null) {

                return "{ \"error\": \"Datei nicht gefunden: "
                        + fileName + "\" }";
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (Exception e) {

            return "{ \"error\": \"Fehler beim Lesen der Datei: "
                    + fileName + "\" }";
        }
    }
}