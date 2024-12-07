package pe.edu.vallegrande.enrollment.domain.mapper;

import org.mapstruct.*;
import pe.edu.vallegrande.enrollment.domain.dto.EnrollmentResponse;
import pe.edu.vallegrande.enrollment.domain.model.Enrollment;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import pe.edu.vallegrande.enrollment.application.util.Student;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnrollmentMapper {

    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);
    Enrollment toEnrollment(Student student, Attorney mother, Attorney father);

}
