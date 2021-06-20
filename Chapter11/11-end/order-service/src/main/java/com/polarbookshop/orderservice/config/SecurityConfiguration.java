package com.polarbookshop.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

@Configuration
public class SecurityConfiguration {

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange(exchange -> exchange
						.anyExchange().authenticated()
				)
				.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
				.requestCache(requestCacheSpec ->
						requestCacheSpec.requestCache((NoOpServerRequestCache.getInstance())))
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.build();
	}
}
