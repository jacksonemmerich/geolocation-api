package pvh.prefeitura.geolocation_api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Geolocation API",
        version = "1.0",
        description = "Api para geolocalização de endereço e coordenadas."
))
public class OpenApiConfig {
}