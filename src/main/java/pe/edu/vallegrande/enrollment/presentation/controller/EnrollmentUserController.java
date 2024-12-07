package pe.edu.vallegrande.enrollment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.EnrollmentService;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequest;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/shared/enrollments${api.version}")
@RequiredArgsConstructor
public class EnrollmentUserController {

    private final EnrollmentService enrollmentService;

    private static final List<String> ALLOWED_ROLES = List.of("DEVELOP", "ESTUDIANTE", "PROFESOR");

    @GetMapping("/active")
    public Mono<ResponseEntity<Flux<EnrollmentResponse>>> getActive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(enrollmentService.findActive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EnrollmentResponse>> getById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return enrollmentService.findById(id)
                                .map(ResponseEntity::ok)
                                .defaultIfEmpty(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

}
