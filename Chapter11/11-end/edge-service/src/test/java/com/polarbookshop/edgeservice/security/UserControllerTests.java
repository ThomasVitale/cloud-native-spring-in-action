package com.polarbookshop.edgeservice.security;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

@WebFluxTest
@Import(SecurityConfig.class)
class UserControllerTests {

	@Autowired
	WebTestClient webClient;

	@MockBean
	ReactiveClientRegistrationRepository clientRegistrationRepository;

	@Test
	void whenNotAuthenticatedThen401() {
		webClient
				.get().uri("/user")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	void whenAuthenticatedThenReturnUser() {
		var expectedUser = new User("jon.snow", "Jon", "Snow", List.of("employee", "customer"));

		webClient
				.mutateWith(configureMockOidcLogin(expectedUser))
				.get().uri("/user")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(User.class)
				.value(user -> {
					System.out.println(user);
					assertThat(user).isNotNull();
					assertThat(user).usingRecursiveComparison().isEqualTo(expectedUser);
				});
	}

	private SecurityMockServerConfigurers.OidcLoginMutator configureMockOidcLogin(User expectedUser) {
		return mockOidcLogin().idToken(builder -> {
			builder.claim(StandardClaimNames.PREFERRED_USERNAME, expectedUser.getUsername());
			builder.claim(StandardClaimNames.GIVEN_NAME, expectedUser.getFirstName());
			builder.claim(StandardClaimNames.FAMILY_NAME, expectedUser.getLastName());
			builder.claim("roles", expectedUser.getRoles());
		});
	}
}