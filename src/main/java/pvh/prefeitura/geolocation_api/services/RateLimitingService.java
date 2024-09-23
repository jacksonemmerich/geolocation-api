package pvh.prefeitura.geolocation_api.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitingService {

    private Bucket bucket;

    @PostConstruct
    public void setup() {
        // Define a taxa de preenchimento do "bucket". 1000 requisições por dia, por exemplo.
        Bandwidth limit = Bandwidth.classic(1000, Refill.greedy(1000, Duration.ofDays(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean tryConsume() {
        // Verifica se há tokens disponíveis (requisições permitidas)
        return bucket.tryConsume(1);
    }
}

