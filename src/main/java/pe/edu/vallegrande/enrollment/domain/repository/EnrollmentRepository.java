package pe.edu.vallegrande.enrollment.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.enrollment.domain.model.Enrollment;
import reactor.core.publisher.Flux;

public interface EnrollmentRepository extends ReactiveMongoRepository<Enrollment, String> {

    Flux<Enrollment> findAllByOrderByCreationDateDesc();
    Flux<Enrollment> findByStateOrderByCreationDateDesc(String state);

}
