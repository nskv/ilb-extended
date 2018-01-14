package main.java.web.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Profile("swagger")
@Configuration
@EnableSwagger2
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SwaggerConfig {

    private static final String AUTH_TOKEN_NAME = "xAuthToken";

    @Bean
    public Docket allApi() {
        return configureDocket("all", RequestHandlerSelectors.any(), PathSelectors.any());
    }



    @Bean
    public Docket portalApi() {
        return configureDocket("portal", RequestHandlerSelectors
                .basePackage("/"), PathSelectors.any());
    }

    private ApiKey xAuthTokenApiKey() {
        return new ApiKey(AUTH_TOKEN_NAME, "X-Auth-Token", "header");
    }

    private SecurityContext xAuthTokenSecurityContext() {
        return SecurityContext
                .builder().securityReferences(xAuthTokenReference())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> xAuthTokenReference() {
        return Lists.newArrayList(new SecurityReference(AUTH_TOKEN_NAME, new AuthorizationScope[0]));
    }

    @SuppressWarnings("Guava")
    private Docket configureDocket(String groupName, Predicate<RequestHandler> predicate, Predicate<String> selector) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .select()
                .apis(predicate)
                .paths(selector)
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(xAuthTokenApiKey()))
                .securityContexts(Lists.newArrayList(xAuthTokenSecurityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
               "default API","default"
                null, null, null, null, null,
                Collections.emptyList()
        );
    }
}
