package pe.edu.vallegrande.enrollment.application.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.enrollment.application.client.*;
import pe.edu.vallegrande.enrollment.application.service.EnrollmentService;
import pe.edu.vallegrande.enrollment.application.service.FamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.*;
import pe.edu.vallegrande.enrollment.domain.mapper.EnrollmentMapper;
import pe.edu.vallegrande.enrollment.domain.mapper.FamilyMapper;
import pe.edu.vallegrande.enrollment.domain.model.Enrollment;
import pe.edu.vallegrande.enrollment.domain.repository.EnrollmentRepository;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import pe.edu.vallegrande.enrollment.application.util.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final StudentWebClient studentWebClient;
    private final AttorneyWebClient attorneyWebClient;
    private final FamilyMapper familyMapper;
    private final FamilyService familyService;
    private final AuthServiceClient authServiceClient;

    @Override
    public Flux<EnrollmentResponse> findAll() {
        return enrollmentRepository.findAllByOrderByCreationDateDesc()
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Flux<EnrollmentResponse> findActive() {
        return enrollmentRepository.findByStateOrderByCreationDateDesc("A")
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Flux<EnrollmentResponse> findInactive() {
        return enrollmentRepository.findByStateOrderByCreationDateDesc("I")
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Flux<EnrollmentResponse> findPending() {
        return enrollmentRepository.findByStateOrderByCreationDateDesc("P")
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Mono<EnrollmentResponse> findById(String id) {
        return enrollmentRepository.findById(id)
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Mono<EnrollmentResponse> create(EnrollmentRequest request) {
        Mono<Student> studentMono = studentWebClient.getStudentById(request.getStudent());
        Mono<Attorney> motherMono = attorneyWebClient.getAttorneyById(request.getMother());
        Mono<Attorney> fatherMono = attorneyWebClient.getAttorneyById(request.getFather());
        return Mono.zip(studentMono, motherMono, fatherMono)
                .flatMap(tuple -> {
                    Enrollment enrollment = enrollmentMapper.toEnrollment(tuple.getT1(), tuple.getT2(), tuple.getT3());
                    return enrollmentRepository.save(enrollment);
                })
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Mono<EnrollmentResponse> update(String id, EnrollmentRequest request) {
        Mono<Student> studentMono = studentWebClient.getStudentById(request.getStudent());
        Mono<Attorney> motherMono = attorneyWebClient.getAttorneyById(request.getMother());
        Mono<Attorney> fatherMono = attorneyWebClient.getAttorneyById(request.getFather());
        return enrollmentRepository.findById(id)
                .flatMap(enrollment -> Mono.zip(studentMono, motherMono, fatherMono)
                        .flatMap(tuple -> {
                            enrollment.setStudent(tuple.getT1());
                            enrollment.setMother(tuple.getT2());
                            enrollment.setFather(tuple.getT3());
                            enrollment.setWriteDate(LocalDateTime.now());
                            return enrollmentRepository.save(enrollment);
                        }))
                .map(enrollmentMapper::toEnrollmentResponse);
    }

    @Override
    public Mono<Void> modifyStatus(String id, String status) {
        return enrollmentRepository.findById(id)
                .flatMap(enrollment -> {
                    enrollment.setState(status);
                    return enrollmentRepository.save(enrollment)
                            .flatMap(data -> {
                                if (status.equals("A")) {
                                    return familyService.getByParents(data.getMother().getDocumentNumber(), data.getFather().getDocumentNumber())
                                            .flatMap(family -> {
                                                if (family.getStudents() == null) {
                                                    family.setStudents(new HashSet<>());
                                                }
                                                family.getStudents().add(data.getStudent());

                                                FamilyRequest mapperFamily = familyMapper.toFamilyRequestDto(family);
                                                return familyService.update(family.getId(), mapperFamily);
                                            })
                                            .switchIfEmpty(
                                                    familyService.create(new FamilyRequest(new HashSet<>(Set.of(data.getStudent())), data.getMother(), data.getFather()))
                                            )
                                            .then();
                                }
                                return Mono.empty();
                            });
                })
                .then();
    }

    @Override
    public Mono<Void> delete(String id) {
        return enrollmentRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> validateTokenAndRoles(String token, List<String> requiredRoles) {
        return authServiceClient.validateToken(token)
                .flatMap(validationResponse -> {
                    if (validationResponse.isValid() && requiredRoles.contains(validationResponse.getRole())) {
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }

}
