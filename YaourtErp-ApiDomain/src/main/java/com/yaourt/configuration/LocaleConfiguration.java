package com.yaourt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {

        // -- Init
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();

        // -- Set
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);

        // -- Commit
        return sessionLocaleResolver;

    }

}
