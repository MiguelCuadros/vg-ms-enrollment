package pe.edu.vallegrande.enrollment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.FamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/directives/families${api.version}")
@RequiredArgsConstructor
public class FamilyAdminController {

    private final FamilyService familyService;

    private static final List<String> ALLOWED_ROLES = List.of("DEVELOP", "PROFESOR", "SECRETARIO", "DIRECTOR");

    @GetMapping("/all")
    public Mono<ResponseEntity<Flux<FamilyResponse>>> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return familyService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(familyService.getAll()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/active")
    public Mono<ResponseEntity<Flux<FamilyResponse>>> getActive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return familyService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(familyService.getActive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/inactive")
    public Mono<ResponseEntity<Flux<FamilyResponse>>> getInactive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return familyService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(familyService.getInactive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<FamilyResponse>> getByStudentId(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String id) {
        String token = authorizationHeader.substring(7);
        return familyService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return familyService.getById(id)
                                .map(ResponseEntity::ok)
                                .defaultIfEmpty(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

}
