package pe.edu.vallegrande.enrollment.application.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.enrollment.application.util.Student;
import reactor.core.publisher.Mono;

@Service
public class StudentWebClient {

    private final WebClient.Builder webClientBuilder;
    private final String studentServiceUrl;

    @Autowired
    public StudentWebClient(WebClient.Builder webClientBuilder, @Value("${spring.microservices.student.url}") String studentServiceUrl) {
        this.webClientBuilder = webClientBuilder;
        this.studentServiceUrl = studentServiceUrl;
    }

    public Mono<Student> getStudentById(String studentId) {
        return webClientBuilder.build()
                .get()
                .uri(studentServiceUrl + "/{studentId}", studentId)
                .retrieve()
                .bodyToMono(Student.class);
    }

}
