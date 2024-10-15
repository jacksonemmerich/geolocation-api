package pvh.prefeitura.geolocation_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import pvh.prefeitura.geolocation_api.dto.GeocodingResponseDTO;

import java.net.URI;

@Service
public class GeocodingService {

    @Autowired
    private RateLimitingService rateLimitingService;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Cacheable("geocoding")
    public GeocodingResponseDTO geocode(String address) throws Exception {
        // Verifica se há tokens disponíveis (requisições permitidas)
        if (!rateLimitingService.tryConsume()) {
            throw new RuntimeException("Limite de requisições atingido. Tente novamente mais tarde.");
        }
        URI uri = new URIBuilder("https://maps.googleapis.com/maps/api/geocode/json")
                .addParameter("address", address)
                .addParameter("key", googleMapsApiKey)
                .build();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(uri);
            String response = EntityUtils.toString(httpClient.execute(request).getEntity());

            JsonNode jsonResponse = objectMapper.readTree(response);
            if (!jsonResponse.get("status").asText().equals("OK")) {
                throw new RuntimeException("Erro na geocodificação: " + jsonResponse.get("status").asText());
            }

            JsonNode location = jsonResponse.get("results").get(0).get("geometry").get("location");

            GeocodingResponseDTO responseDTO = new GeocodingResponseDTO();
            responseDTO.setFormattedAddress(jsonResponse.get("results").get(0).get("formatted_address").asText());
            responseDTO.setLatitude(location.get("lat").asDouble());
            responseDTO.setLongitude(location.get("lng").asDouble());

            return responseDTO;
        }
    }

    @Cacheable("reverseGeocoding")
    public GeocodingResponseDTO reverseGeocode(double latitude, double longitude) throws Exception {
        // Verifica se há tokens disponíveis (requisições permitidas)
        if (!rateLimitingService.tryConsume()) {
            throw new RuntimeException("Limite de requisições atingido. Tente novamente mais tarde.");
        }
        URI uri = new URIBuilder("https://maps.googleapis.com/maps/api/geocode/json")
                .addParameter("latlng", latitude + "," + longitude)
                .addParameter("key", googleMapsApiKey)
                .build();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(uri);
            String response = EntityUtils.toString(httpClient.execute(request).getEntity());

            JsonNode jsonResponse = objectMapper.readTree(response);
            if (!jsonResponse.get("status").asText().equals("OK")) {
                throw new RuntimeException("Erro na geocodificação reversa: " + jsonResponse.get("status").asText());
            }

            JsonNode location = jsonResponse.get("results").get(0).get("geometry").get("location");

            GeocodingResponseDTO responseDTO = new GeocodingResponseDTO();
            responseDTO.setFormattedAddress(jsonResponse.get("results").get(0).get("formatted_address").asText());
            responseDTO.setLatitude(latitude);
            responseDTO.setLongitude(longitude);

            return responseDTO;
        }
    }
}

