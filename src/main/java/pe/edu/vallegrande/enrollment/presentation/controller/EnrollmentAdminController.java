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
@RequestMapping("/directives/enrollments${api.version}")
@RequiredArgsConstructor
public class EnrollmentAdminController {

    private final EnrollmentService enrollmentService;

    private static final List<String> ALLOWED_ROLES = List.of("DEVELOP", "PROFESOR", "SECRETARIO", "DIRECTOR");

    @GetMapping("/all")
    public Mono<ResponseEntity<Flux<EnrollmentResponse>>> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(enrollmentService.findAll()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

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

    @GetMapping("/inactive")
    public Mono<ResponseEntity<Flux<EnrollmentResponse>>> getInactive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(enrollmentService.findInactive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/pending")
    public Mono<ResponseEntity<Flux<EnrollmentResponse>>> getPending(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(enrollmentService.findPending()));
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

    @PostMapping("/create")
    public Mono<ResponseEntity<EnrollmentResponse>> create(@RequestHeader("Authorization") String authorizationHeader, @RequestBody EnrollmentRequest request) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return enrollmentService.create(request)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<EnrollmentResponse>> update(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id, @RequestBody EnrollmentRequest request) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return enrollmentService.update(id, request)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Void>> modifyStatus(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id, @RequestBody String state) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return enrollmentService.modifyStatus(id, state)
                                .then(Mono.just(ResponseEntity.ok().build()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        String token = authorizationHeader.substring(7);
        return enrollmentService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return enrollmentService.delete(id)
                                .then(Mono.just(ResponseEntity.ok().build()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

}
