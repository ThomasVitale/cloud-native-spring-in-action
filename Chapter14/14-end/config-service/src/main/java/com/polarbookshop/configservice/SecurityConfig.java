package com.polarbookshop.configservice;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain springSecurityFilter(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.authorizeRequests(authorize -> authorize
						.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
						.anyRequest().authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.build();
	}

}
