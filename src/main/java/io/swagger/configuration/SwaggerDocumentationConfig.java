package io.swagger.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

  ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Busfor API")
        .description("No description provided (generated by Swagger Codegen)")
        .license("")
        .licenseUrl("http://unlicense.org")
        .termsOfServiceUrl("")
        .version("1.0.0")
        .contact(new Contact("", "", ""))
        .build();
  }

  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(
            Predicates.or(
                PathSelectors.regex("/actuator/health"),
                PathSelectors.regex("/v1/.*")
            )
        )
        .build()
        .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
        .apiInfo(apiInfo())
        .tags(
            new Tag("infrastructure", "Infrastructure services"),
            new Tag("operation-handler", "Actuator")
        );
  }

}
