package pvh.prefeitura.geolocation_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pvh.prefeitura.geolocation_api.dto.GeocodingRequestDTO;
import pvh.prefeitura.geolocation_api.dto.GeocodingResponseDTO;
import pvh.prefeitura.geolocation_api.dto.ReverseGeocodingRequestDTO;
import pvh.prefeitura.geolocation_api.services.GeocodingService;

@RestController
@RequestMapping("/geocoding")
public class GeocodingController {

    @Autowired
    private GeocodingService geocodingService;

    @PostMapping("/geocode")
    public GeocodingResponseDTO geocode(@RequestBody GeocodingRequestDTO request) throws Exception {
        return geocodingService.geocode(request.getAddress());
    }

    @PostMapping("/reverse")
    public GeocodingResponseDTO reverseGeocode(@RequestBody ReverseGeocodingRequestDTO request) throws Exception {
        return geocodingService.reverseGeocode(request.getLatitude(), request.getLongitude());
    }
}
