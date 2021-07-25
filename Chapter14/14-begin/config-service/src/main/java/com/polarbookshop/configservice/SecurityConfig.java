package com.polarbookshop.configservice;

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
				.authorizeRequests(request -> request.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.build();
	}

}
