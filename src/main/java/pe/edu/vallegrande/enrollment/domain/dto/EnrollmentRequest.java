package pe.edu.vallegrande.enrollment.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {

    private String student;
    private String mother;
    private String father;

}
