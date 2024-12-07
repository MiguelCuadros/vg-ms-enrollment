package pe.edu.vallegrande.enrollment.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import pe.edu.vallegrande.enrollment.application.util.Student;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "family")
public class Family {

    @MongoId
    @Field("_id")
    private String id;

    @Field("students")
    private Set<Student> students = new HashSet<>();

    @Field("mother")
    private Attorney mother;

    @Field("father")
    private Attorney father;

    @Field("creationDate")
    private LocalDateTime creationDate=LocalDateTime.now();

    @Field("writeDate")
    private LocalDateTime writeDate=LocalDateTime.now();

    @Field("state")
    private String state="A";

}
