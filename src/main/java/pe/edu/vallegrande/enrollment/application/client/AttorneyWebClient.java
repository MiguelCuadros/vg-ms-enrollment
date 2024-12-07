package pe.edu.vallegrande.enrollment.application.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import reactor.core.publisher.Mono;

@Service
public class AttorneyWebClient {

    private final WebClient.Builder webClientBuilder;
    private final String attorneyServiceUrl;

    @Autowired
    public AttorneyWebClient(WebClient.Builder webClientBuilder, @Value("${spring.microservices.attorney.url}") String attorneyServiceUrl) {
        this.webClientBuilder = webClientBuilder;
        this.attorneyServiceUrl = attorneyServiceUrl;
    }

    public Mono<Attorney> getAttorneyById(String attorneyId) {
        return webClientBuilder.build()
                .get()
                .uri(attorneyServiceUrl + "/{attorneyId}", attorneyId)
                .retrieve()
                .bodyToMono(Attorney.class);
    }

}
