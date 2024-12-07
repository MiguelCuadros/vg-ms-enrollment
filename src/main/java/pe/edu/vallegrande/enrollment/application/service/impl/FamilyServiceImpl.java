package pe.edu.vallegrande.enrollment.application.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.enrollment.application.client.AuthServiceClient;
import pe.edu.vallegrande.enrollment.application.service.FamilyService;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyRequest;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponse;
import pe.edu.vallegrande.enrollment.domain.mapper.FamilyMapper;
import pe.edu.vallegrande.enrollment.domain.model.Family;
import pe.edu.vallegrande.enrollment.domain.repository.FamilyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;
    private final AuthServiceClient authServiceClient;

    @Override
    public Flux<FamilyResponse> getAll() {
        return familyRepository.findAllByOrderByCreationDateDesc()
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Flux<FamilyResponse> getActive() {
        return familyRepository.findByStateOrderByCreationDateDesc("A")
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Flux<FamilyResponse> getInactive() {
        return familyRepository.findByStateOrderByCreationDateDesc("I")
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Mono<FamilyResponse> getById(String id) {
        return familyRepository.findById(id)
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Mono<Family> getByParents(String motherId, String fatherId) {
        return familyRepository.findByMother_DocumentNumberAndFather_DocumentNumber(motherId, fatherId);
    }

    @Override
    public Mono<FamilyResponse> create(FamilyRequest request) {
        Family family = familyMapper.toFamily(request);
        return familyRepository.save(family)
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Mono<FamilyResponse> update(String id, FamilyRequest request) {
        return familyRepository.findById(id)
                .flatMap(family -> {
                    family.setStudents(request.getStudents());
                    family.setMother(request.getMother());
                    family.setFather(request.getFather());
                    family.setWriteDate(LocalDateTime.now());
                    return familyRepository.save(family);
                })
                .map(familyMapper::toFamilyResponse);
    }

    @Override
    public Mono<Void> modifyStatus(String id, String status) {
        return familyRepository.findById(id)
                .flatMap(family -> {
                    family.setState(status);
                    return familyRepository.save(family);
                })
                .then();
    }

    @Override
    public Mono<Void> delete(String id) {
        return familyRepository.deleteById(id);
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
