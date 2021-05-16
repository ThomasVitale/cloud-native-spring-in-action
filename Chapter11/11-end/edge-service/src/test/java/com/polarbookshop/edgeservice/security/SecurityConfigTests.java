package com.polarbookshop.edgeservice.security;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

@WebFluxTest
@Import(SecurityConfig.class)
class SecurityConfigTests {

	@Autowired
	WebTestClient webClient;

	@MockBean
	ReactiveClientRegistrationRepository clientRegistrationRepository;

	@Test
	void whenLogoutNotAuthenticatedAndNoCsrfTokenThen403() {
		webClient
				.post().uri("/logout")
				.exchange()
				.expectStatus().isForbidden();
	}

	@Test
	void whenLogoutAuthenticatedAndNoCsrfTokenThen403() {
		webClient
				.mutateWith(mockOidcLogin())
				.post().uri("/logout")
				.exchange()
				.expectStatus().isForbidden();
	}

	@Test
	void whenLogoutAuthenticatedAndWithCsrfTokenThen302() {
		when(clientRegistrationRepository.findByRegistrationId("test"))
				.thenReturn(Mono.just(testClientRegistration()));

		webClient
				.mutateWith(mockOidcLogin())
				.mutateWith(csrf())
				.post().uri("/logout")
				.exchange()
				.expectStatus().isFound();
	}

	private ClientRegistration testClientRegistration() {
		return ClientRegistration.withRegistrationId("test")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.clientId("test")
				.authorizationUri("https://sso.polarbookshop.org/auth")
				.tokenUri("https://sso.polarbookshop.org/token")
				.redirectUri("https://polarbookshop.org")
				.build();
	}

}