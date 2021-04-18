package com.polarbookshop.edgeservice.security;

import reactor.core.publisher.Mono;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("user")
	public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
		User user = new User();
		user.setId(oidcUser.getSubject());
		user.setUsername(oidcUser.getPreferredUsername());
		user.setFirstName(oidcUser.getGivenName());
		user.setLastName(oidcUser.getFamilyName());
		user.setRoles(oidcUser.getClaimAsStringList("roles"));
		return Mono.just(user);
	}
}
