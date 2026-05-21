# Smart City NGSI Server

Dieses Projekt entsteht im Rahmen der Veranstaltung  
„Informations- und Kommunikationstechnologie für Smart Cities“.

Ziel ist die schrittweise Entwicklung eines Java-basierten NGSI-Servers
für Smart-City-Anwendungsfälle, insbesondere Smart Parking.

---

## Inhalte

- Übungsblatt 1:
  - Anforderungen
  - Architektur
  - Bibliotheken

- Übungsblatt 2:
  - Implementierung eines HTTP-Servers
  - NGSI-Endpunkte
  - Funktionale Tests

- Weitere Übungsblätter werden ergänzt

---

## Technologien

- Java
- Maven
- Java HTTP Server (`com.sun.net.httpserver.HttpServer`)
- JSON
- Git & GitHub

---

## Projektstruktur

```text
src/main/java/de/hsrm/smartcity/ngsiserver
├── Main.java
└── SimpleHttpServer.java

src/main/resources
├── Vehicle_Example.json
└── Parking_Entity_GeoJSON.json
```

---

## Endpunkte

| Methode | Endpoint   | Beschreibung                          |
| ------- | ---------- | ------------------------------------- |
| GET     | `/vehicle` | Liefert NGSI-Daten zu einem Fahrzeug  |
| POST    | `/vehicle` | Liefert NGSI-Daten zu einem Fahrzeug  |
| GET     | `/parking` | Liefert NGSI-Daten zu einem Parkplatz |
| POST    | `/parking` | Liefert NGSI-Daten zu einem Parkplatz |

---

## Projekt starten

Server starten:

```bash
mvnw.cmd compile
```

Danach:

```bash
mvnw.cmd exec:java -Dexec.mainClass="de.hsrm.smartcity.ngsiserver.Main"
```

Alternativ direkt über VS Code:

- Rechtsklick auf `Main.java`
- `Run Java`

---

## Endpunkte testen

### Vehicle Endpoint

GET Vehicle
curl.exe http://localhost:8080/vehicle
POST Vehicle
curl.exe -X POST http://localhost:8080/vehicle

### Parking Endpoint

GET Parking
curl.exe http://localhost:8080/parking
POST Parking
curl.exe -X POST http://localhost:8080/parking

---

## Beispielausgabe

### Vehicle

```json
{
  "id": "urn:ngsi-ld:Vehicle:A4567",
  "type": "Vehicle"
}
```

### Parking

```json
{
  "id": "urn:ngsi-ld:ParkingSpot:001",
  "type": "ParkingSpot"
}
```

---

## Ziel des Projekts

Das Projekt demonstriert die Umsetzung eines einfachen
NGSI-basierten HTTP-Servers für Smart-City-Anwendungsfälle.

Dabei werden:

- offene Schnittstellen,
- HTTP-Kommunikation,
- REST-artige Endpunkte
- und NGSI-konforme JSON-Daten

bereitgestellt.

---
