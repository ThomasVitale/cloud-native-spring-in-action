package com.polarbookshop.edgeservice;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {

	@Bean
	KeyResolver keyResolver() {
		return exchange -> exchange.getPrincipal()
				.flatMap(p -> Mono.just(p.getName()))
				.defaultIfEmpty("ANONYMOUS");
	}
	
}
