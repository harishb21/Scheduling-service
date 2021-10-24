package com.ct.scheduling;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	  public Docket api() {
	      return new Docket(DocumentationType.SWAGGER_2)
	    	  .groupName("scheduling-service")
	          .select()
	          .apis(RequestHandlerSelectors.basePackage("com.ct.scheduling.controller"))
	          .paths(PathSelectors.any())
	          .build()
	          .apiInfo(getApiInfo());
	  }

	  private ApiInfo getApiInfo() {
	      return new ApiInfo(
	    	"Scheduling Rest Api",
	          "Scheduling service Rest API Documentation",
	          "1.0", 
	          "urn:tos",
	          new Contact("Harish. B", "www.citiusTech.com", "harish.bandhamravuri@gmail.com"),
	          "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
	  }
}
