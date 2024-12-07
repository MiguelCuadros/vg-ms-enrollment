package pe.edu.vallegrande.enrollment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.FamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public/families${api.version}")
@RequiredArgsConstructor
public class FamilyPublicController {

    private final FamilyService familyService;

    @GetMapping("/all")
    public Flux<FamilyResponse> getAll() {
        return familyService.getAll();
    }

    @GetMapping("/active")
    public Flux<FamilyResponse> getActive() {
        return familyService.getActive();
    }

    @GetMapping("/inactive")
    public Flux<FamilyResponse> getInactive() {
        return familyService.getInactive();
    }

    @GetMapping("/{id}")
    public Mono<FamilyResponse> getByStudentId(@PathVariable String id) {
        return familyService.getById(id);
    }

    @PatchMapping("/{id}")
    public Mono<Void> modifyStatus(@PathVariable String id, @RequestBody String state) {
        return familyService.modifyStatus(id, state);
    }

}
