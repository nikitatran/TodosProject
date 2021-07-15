package com.cognixia.jump.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * On Browser, go to URL: http://localhost:8080/swagger-ui.html 
 *
 * On Postman, do a get request to: http://localhost:8080/v2/api-docs
 * 
 * */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {

		return  new Docket(DocumentationType.SWAGGER_2)
					  .select()
					  .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
					  .paths(PathSelectors.any())
					  .build()
					  .apiInfo( apiDetails() );
	}

	private ApiInfo apiDetails() {
		
		return new ApiInfo("Todo List API", // title
				"API for a database with Users and their Todo list with details", // description
				"1.0", // version
				"Free to use", // terms of service (url or message)
				new Contact("Raymond Nazaryan",
						"https://github.com/nikitatran/TodosProject",
						"REDACTED"), // contact details
				"MIT License", // license
				"https://github.com/nikitatran/TodosProject", // license url
				Collections.emptyList() // vendors
				);
	}
}
