package pvh.prefeitura.geolocation_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GeolocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeolocationApiApplication.class, args);
	}

}
