package com.isobar.wallet.management.user.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * The Constant DEFAULT_CONTACT.
     */
    private static final Contact DEFAULT_CONTACT = new Contact("isbar user service", "isobar.com", "xz.com");
    /**
     * The Constant DEFAULT_API_INFO.
     */
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("user", "manage user profile", "V1", "default terms",
            DEFAULT_CONTACT, "default", "default");
    /**
     * The Constant DEFAULT_PRODUCES_AND_CONSUMES.
     */
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json"));
    private Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    /**
     * Api.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        logger.debug(
                "api|invoke|add|new ResponseMessageBuilder().code(SWAGGER_SUCCESS_CODE_1).message(SWAGGER_SUCCESS_MESSAGE_1).build()");
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.isobar.wallet.management")).paths(PathSelectors.any()).build().apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES).consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
