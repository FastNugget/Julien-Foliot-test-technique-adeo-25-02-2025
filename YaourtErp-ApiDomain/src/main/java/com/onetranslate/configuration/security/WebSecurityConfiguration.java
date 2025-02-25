package com.onetranslate.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {


    // -- CONF ---------------------------------------------------------------------------------------------------------

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
               // -- Needed for H2 database console
               //.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionCustomizer -> frameOptionCustomizer.disable()))
               // -- Not required if you have good architecture
               //.cors(cors -> cors.disable())
               .csrf(crsf -> crsf.disable()) // We don't need CSRF for this example
               .authorizeHttpRequests(authorizer -> authorizer.anyRequest().permitAll()) // -- ApiKeyFilter is in charge of authentication (see WebSecurityConfig)
               .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .build();

    }



}