package pe.edu.vallegrande.enrollment.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FamilyRepository extends ReactiveMongoRepository<Family, String> {

    Flux<Family> findAllByOrderByCreationDateDesc();
    Flux<Family> findByStateOrderByCreationDateDesc(String state);
    Mono<Family> findByMother_DocumentNumberAndFather_DocumentNumber(String motherDocumentNumber, String fatherDocumentNumber);

}
