package com.arcticgreetings.greetingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GreetingConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingConfig.class);

    @Bean
    @Profile("dev")
    public String devBean() {
        LOGGER.info("Development bean registered");
        return "Dev";
    }

    @Bean
    @Profile("prod")
    public String prodBean() {
        LOGGER.info("Production bean registered");
        return "Prod";
    }
}
