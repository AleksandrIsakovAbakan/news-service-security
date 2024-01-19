package com.example.newsservicesecurity.configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("NewsService-API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {

        List<Server> serverList = new ArrayList<>();
        serverList.add(new Server().url("http://localhost:8086/").description("localhost"));
        return new OpenAPI()
                .info(new Info()
                        .title("NewsService-API")
                        .description("API for news service")
                        .version("1.0")
                        .contact(new Contact().name("Aleksandr").url("http://81.177.6.228:8080").email("aaa@aaaa.aa"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(serverList);
    }
}

