package com.ls.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by ls on 2019/10/10.
 */
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ls.es.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    //文档基础信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            // 文档标题
            .title("es")
            // 文档描述
            .description("接口文档")
            .termsOfServiceUrl("")
            .version("v1.0")
            .build();
    }


}
