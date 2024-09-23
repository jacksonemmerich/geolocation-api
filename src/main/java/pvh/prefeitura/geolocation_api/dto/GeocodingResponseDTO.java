package pvh.prefeitura.geolocation_api.dto;

import lombok.Data;

@Data
public class GeocodingResponseDTO {
    private String formattedAddress;
    private double latitude;
    private double longitude;
}

