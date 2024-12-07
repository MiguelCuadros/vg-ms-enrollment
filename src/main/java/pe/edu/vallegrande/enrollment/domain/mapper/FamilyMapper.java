package pe.edu.vallegrande.enrollment.domain.mapper;

import org.mapstruct.*;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyRequest;
import pe.edu.vallegrande.enrollment.domain.dto.FamilyResponse;
import pe.edu.vallegrande.enrollment.domain.model.Family;

import java.util.HashSet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FamilyMapper {

    FamilyResponse toFamilyResponse(Family family);
    Family toFamily(FamilyRequest familyRequest);
    @Mapping(target = "student", source = "students")
    default FamilyRequest toFamilyRequestDto(Family family) {
        FamilyRequest dto = new FamilyRequest();
        dto.setStudents(family.getStudents() != null ? family.getStudents() : new HashSet<>());
        dto.setMother(family.getMother());
        dto.setFather(family.getFather());
        return dto;
    }

}
