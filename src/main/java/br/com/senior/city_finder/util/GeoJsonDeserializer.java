package br.com.senior.city_finder.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.IOException;

/**
 * Created by rsorage on 8/27/17.
 */
public class GeoJsonDeserializer extends JsonDeserializer<GeoJsonPoint> {

    private final static String GEOJSON_TYPE_POINT = "Point";

    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";

    @Override
    public GeoJsonPoint deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        final JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);

        double x = 0;
        double y = 0;

        if(GEOJSON_TYPE_POINT.equalsIgnoreCase(type)) {
            x = coordsNode.get(0).asDouble();
            y = coordsNode.get(1).asDouble();
        }
        else
            System.err.printf("Could not deserialize %s!\n", tree.asText());

        final GeoJsonPoint point = new GeoJsonPoint(x, y);

        return point;
    }
}
