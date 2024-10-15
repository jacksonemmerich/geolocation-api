package pvh.prefeitura.geolocation_api.dto;

import lombok.Data;

@Data
public class ReverseGeocodingRequestDTO {
    private double latitude;
    private double longitude;

}

