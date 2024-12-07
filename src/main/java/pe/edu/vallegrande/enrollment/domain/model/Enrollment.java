package pe.edu.vallegrande.enrollment.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;
import pe.edu.vallegrande.enrollment.application.util.Attorney;
import pe.edu.vallegrande.enrollment.application.util.Student;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "enrollment")
public class Enrollment {

    @MongoId
    @Field("_id")
    private String id;

    @Field("student")
    private Student student;

    @Field("mother")
    private Attorney mother;

    @Field("father")
    private Attorney father;

    @Field("creationDate")
    private LocalDateTime creationDate=LocalDateTime.now();

    @Field("writeDate")
    private LocalDateTime writeDate=LocalDateTime.now();

    @Field("state")
    private String state="P";

}