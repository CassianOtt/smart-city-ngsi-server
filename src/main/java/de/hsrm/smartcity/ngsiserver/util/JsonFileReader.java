package de.hsrm.smartcity.ngsiserver.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFileReader {

    public static String readJsonFile(String fileName) {

        try {

            InputStream inputStream = JsonFileReader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (inputStream == null) {

                return """
                        {
                          "error": "JSON file not found"
                        }
                        """;
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (Exception e) {

            return """
                    {
                      "error": "JSON file could not be read"
                    }
                    """;
        }
    }
}