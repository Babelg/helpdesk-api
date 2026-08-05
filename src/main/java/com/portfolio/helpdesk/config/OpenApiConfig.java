package com.portfolio.helpdesk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpdeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Help Desk API")
                        .description("API REST para gestão de chamados internos (TI, RH, Facilities). " +
                                "Projeto de portfólio construído com Spring Boot, JPA e PostgreSQL.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Guilherme Babel Machado")
                                .url("https://github.com/Babelg?tab=repositories")));
    }
}
