package fr.dauphine.miageif.CalendrierSpec.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    //Swagger available at : http://localhost:5154/swagger-ui/index.html
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(new Info(){}.title("API Doc").version("1.0.0").description("Calendrier Service API"));
    }
}
