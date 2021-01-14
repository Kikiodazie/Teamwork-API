package com.odazie.teamworkapi.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()));
    }



    private ApiKey apiKey() {
        return new ApiKey("Bearer ", "Authorization", "header");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TeamWork REST API")
                .description("Teamwork is a side project I developed for learning purposes.The idea is to model an internal social network for employees of an organization. The goal of this API is to facilitate more interaction between colleagues " +
                        "and promote team bonding(In this time of COVID-19 this may be useful for coping with the new way of working). \n\n" +
                        "Make sure to add the token generated from login in the Authorization header.\n\n Currently the is a Bug for the API Response Specification on this swagger doc, will fix")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }

}
