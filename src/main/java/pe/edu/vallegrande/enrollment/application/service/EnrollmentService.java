package pe.edu.vallegrande.enrollment.application.service;

import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequest;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EnrollmentService {

    Flux<EnrollmentResponse> findAll();
    Flux<EnrollmentResponse> findActive();
    Flux<EnrollmentResponse> findInactive();
    Flux<EnrollmentResponse> findPending();
    Mono<EnrollmentResponse> findById(String id);
    Mono<EnrollmentResponse> create(EnrollmentRequest request);
    Mono<EnrollmentResponse> update(String id, EnrollmentRequest request);
    Mono<Void> modifyStatus(String id, String status);
    Mono<Void> delete(String id);
    Mono<Boolean> validateTokenAndRoles(String token, List<String> requiredRoles);

}
