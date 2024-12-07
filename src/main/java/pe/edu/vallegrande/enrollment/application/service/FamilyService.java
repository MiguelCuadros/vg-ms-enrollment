package pe.edu.vallegrande.enrollment.application.service;

import pe.edu.vallegrande.enrollment.domain.dto.FamilyRequest;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponse;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FamilyService {

    Flux<FamilyResponse> getAll();
    Flux<FamilyResponse> getActive();
    Flux<FamilyResponse> getInactive();
    Mono<FamilyResponse> getById(String id);
    Mono<Family> getByParents(String motherId, String fatherId);
    Mono<FamilyResponse> create(FamilyRequest request);
    Mono<FamilyResponse> update(String id, FamilyRequest request);
    Mono<Void> modifyStatus(String id, String status);
    Mono<Void> delete(String id);
    Mono<Boolean> validateTokenAndRoles(String token, List<String> requiredRoles);

}
