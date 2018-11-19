package com.drees.cognito.config.swagger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  
  @Value("${swagger.prefix.url}")
  private String apiPrefix;  
  
  @Bean
  public Docket baseConfig(final ServletContext servletContext) {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any()).build().apiInfo(apiInfo()).pathProvider(new RelativePathProvider(servletContext) {

          @Override
          public String getApplicationBasePath() {
              if (apiPrefix.equals("/")) {
                  return super.getApplicationBasePath();
              }
              return apiPrefix + super.getApplicationBasePath();
          }
        });
  }
    
    private ApiInfo apiInfo() {
      return new ApiInfoBuilder().title("Dungeon Builder Cognito API Documentation")
          .description("The following contains DungeonBuilder Cognito API Documentation")
          .contact(new Contact("Russell Sowell", "https://www.russellsowell.com", "russellsowell88@gmail.com")).build();
    }
}
