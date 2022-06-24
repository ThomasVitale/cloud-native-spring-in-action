package com.polarbookshop.edgeservice.config;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

	@Bean
	public KeyResolver keyResolver() {
		return exchange -> Mono.just("anonymous");
	}
	
}
