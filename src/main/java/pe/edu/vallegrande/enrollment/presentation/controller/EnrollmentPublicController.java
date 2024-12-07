package pe.edu.vallegrande.enrollment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.enrollment.application.service.EnrollmentService;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentRequest;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public/enrollments${api.version}")
@RequiredArgsConstructor
public class EnrollmentPublicController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/all")
    public Flux<EnrollmentResponse> getAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/active")
    public Flux<EnrollmentResponse> getActive() {
        return enrollmentService.findActive();
    }

    @GetMapping("/inactive")
    public Flux<EnrollmentResponse> getInactive() {
        return enrollmentService.findInactive();
    }

    @GetMapping("/pending")
    public Flux<EnrollmentResponse> getPending() {
        return enrollmentService.findPending();
    }

    @GetMapping("/{id}")
    public Mono<EnrollmentResponse> getById(@PathVariable String id) {
        return enrollmentService.findById(id);
    }

    @PostMapping("/create")
    public Mono<EnrollmentResponse> create(@RequestBody EnrollmentRequest request) {
        return enrollmentService.create(request);
    }

    @PutMapping("/update/{id}")
    public Mono<EnrollmentResponse> update(@PathVariable String id, @RequestBody EnrollmentRequest request) {
        return enrollmentService.update(id, request);
    }

    @PatchMapping("/{id}")
    public Mono<Void> modifyStatus(@PathVariable String id, @RequestBody String state) {
        return enrollmentService.modifyStatus(id, state);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return enrollmentService.delete(id);
    }

}
