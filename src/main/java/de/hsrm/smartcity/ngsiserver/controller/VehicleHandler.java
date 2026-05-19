package de.hsrm.smartcity.ngsiserver.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.hsrm.smartcity.ngsiserver.util.JsonFileReader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class VehicleHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String response;
        int statusCode;

        if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("POST")) {
            response = JsonFileReader.readJsonFile("Vehicle_Example.json");
            statusCode = 200;
        } else {
            response = """
                    {
                      "error": "Method not supported"
                    }
                    """;
            statusCode = 405;
        }

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}