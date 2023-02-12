package com.ecommerce.microcommerce.web.configuration;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Api("API pour les opérations CRUD sur les produits.")
@Configuration
public class SwaggerConfig {

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.ecommerce.micrommerce.web"))
              .paths(PathSelectors.regex("/product.*"))
              .build();

    }
}
