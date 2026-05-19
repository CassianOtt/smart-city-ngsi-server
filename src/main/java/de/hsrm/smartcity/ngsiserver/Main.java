package de.hsrm.smartcity.ngsiserver;

public class Main {

    public static void main(String[] args) {
        SimpleHttpServer server = new SimpleHttpServer();
        server.start();
    }
}