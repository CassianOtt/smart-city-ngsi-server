# Smart City NGSI Server

Dieses Projekt entsteht im Rahmen der Veranstaltung
"Informations- und Kommunikationstechnologie für Smart Cities".

Ziel ist die schrittweise Entwicklung eines Java-basierten NGSI-Servers
für Smart-City-Anwendungsfälle, insbesondere Smart Parking.

## Inhalte

- Übungsblatt 1: Anforderungen, Architektur und Bibliotheken
- Übungsblatt 2: Implementierung eines HTTP-Servers
- Weitere Übungsblätter werden ergänzt

## Technologien

- Java
- Spring Boot
- Spring Web
- Jackson
- Maven

## Endpunkte

| Methode | Endpoint | Beschreibung |
|---|---|---|
| GET | `/vehicle` | Liefert NGSI-Daten zu einem Fahrzeug |
| POST | `/vehicle` | Liefert NGSI-Daten zu einem Fahrzeug |
| GET | `/parking` | Liefert NGSI-Daten zu einem Parkplatz |
| POST | `/parking` | Liefert NGSI-Daten zu einem Parkplatz |

## Projekt starten

```bash
mvn spring-boot:run
