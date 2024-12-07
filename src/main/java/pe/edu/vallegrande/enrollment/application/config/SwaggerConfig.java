package pe.edu.vallegrande.enrollment.application.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI enrollmentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enrollment API")
                        .description("Enrollment API implemented with Spring Boot RESTful service")
                        .version("v1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Enrollment Wiki Documentation")
                        .url("https://vallegrande.edu.pe/teacher")
                );
    }

}
