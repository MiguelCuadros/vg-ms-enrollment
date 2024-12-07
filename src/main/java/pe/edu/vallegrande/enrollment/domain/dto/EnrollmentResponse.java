package pe.edu.vallegrande.enrollment.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import pe.edu.vallegrande.enrollment.application.util.Student;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {

    private String id;
    private Student student;
    private Attorney mother;
    private Attorney father;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime writeDate;

}
