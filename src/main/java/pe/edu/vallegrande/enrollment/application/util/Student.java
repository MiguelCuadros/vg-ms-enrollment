package pe.edu.vallegrande.enrollment.application.util;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"documentNumber"})
public class Student {

    private String idStudent;
    private String name;
    private String lastName;
    private String documentNumber;
    private String gender;
    private LocalDate birthDate;
    private String level;
    private String grade;
    private String section;

}
