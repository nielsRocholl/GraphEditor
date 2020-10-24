package nl.rug.oop.grapheditor.io;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Used for parsing the Json file.
 */
public class json {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    public static JsonNode parse(String src) throws IOException {

        return objectMapper.readTree(src);
    }
}
