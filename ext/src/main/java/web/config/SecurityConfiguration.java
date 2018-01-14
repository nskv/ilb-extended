package main.java.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AppProperty appProperty;

    private UserAccountService userAccountService;

    private CollectorAccountService collectorAccountService;

    @Autowired
    public SecurityConfiguration(AppProperty appProperty, UserAccountService userAccountService,
                                 CollectorAccountService collectorAccountService) {
        this.appProperty = appProperty;
        this.collectorAccountService = collectorAccountService;
        this.userAccountService = userAccountService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()

                .formLogin().loginPage("/api/login")
                .successHandler(new RestAuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/manage/health").permitAll()
                .antMatchers("/manage/**").authenticated()
                .anyRequest().permitAll().and()

                .requestCache()
                .requestCache(new NullRequestCache())
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()

                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (appProperty.getEnv() == AppProperty.Environment.COLLECTOR) {
            auth.userDetailsService(collectorAccountService)
                .passwordEncoder(collectorAccountService.passwordEncoder());
        } else {
            auth.userDetailsService(userAccountService)
                .passwordEncoder(userAccountService.passwordEncoder());
        }
    }

}
