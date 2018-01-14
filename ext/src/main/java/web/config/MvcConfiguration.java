package main.java.web.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@Order(SecurityProperties.IGNORED_ORDER)
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }

    @Lazy
    @Bean
    public OptionalPageableArgumentResolver optionalPageableArgumentResolver() {
        return new OptionalPageableArgumentResolver();
    }

    @Lazy
    @Bean
    public OptionalPredicateArgumentResolver optionalPredicateArgumentResolver() {
        return new OptionalPredicateArgumentResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false)
                .addResolver(new SinglePageResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "PATCH", "DELETE")
                .allowedHeaders(
                        "X-Auth-Token", "Content-Type", "X-Requested-With",
                        "accept", "Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(optionalPageableArgumentResolver());
        argumentResolvers.add(optionalPredicateArgumentResolver());
    }
}
