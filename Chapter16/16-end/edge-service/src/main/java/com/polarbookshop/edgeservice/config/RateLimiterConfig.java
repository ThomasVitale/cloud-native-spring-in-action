package com.polarbookshop.edgeservice.config;

import java.security.Principal;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

	@Bean
	KeyResolver keyResolver() {
		return exchange -> exchange.getPrincipal()
				.map(Principal::getName)
				.defaultIfEmpty("anonymous");
	}

}
