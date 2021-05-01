package com.polarbookshop.edgeservice.security;

import java.util.Objects;

import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;

@Configuration
public class SecurityConfig {

	@Bean
	public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
		return new WebSessionServerOAuth2AuthorizedClientRepository();
	}

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {
		return http
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/", "/*.css", "/*.js", "/favicon.ico").permitAll()
						.pathMatchers(HttpMethod.GET, "/**").permitAll()
						.anyExchange().authenticated()
				)
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
				.oauth2Login(login -> login.authorizedClientRepository(authorizedClientRepository()))
				.logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)))
				.csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
				.build();
	}

	private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
		var oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
		return oidcLogoutSuccessHandler;
	}

	@Bean
	WebFilter csrfWebFilter() {
		return (exchange, chain) -> {
			String key = CsrfToken.class.getName();
			Mono<CsrfToken> csrfToken = exchange.getAttributes().containsKey(key) ? exchange.getAttribute(key) : Mono.empty();
			return Objects.requireNonNull(csrfToken)
					.doOnSuccess(token -> {}) // Do nothing. Just subscribe.
					.then(chain.filter(exchange));
		};
	}
}
