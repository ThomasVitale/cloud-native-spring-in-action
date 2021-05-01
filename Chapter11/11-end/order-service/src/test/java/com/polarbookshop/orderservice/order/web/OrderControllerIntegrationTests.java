package com.polarbookshop.orderservice.order.web;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import com.polarbookshop.orderservice.order.event.OrderAcceptedMessage;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestChannelBinderConfiguration.class)
@AutoConfigureWebTestClient
@Testcontainers
class OrderControllerIntegrationTests {

	@Container
	static final PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"));

	@Container
	static final KeycloakContainer keycloak = new KeycloakContainer("jboss/keycloak:12.0.4")
			.withRealmImportFile("keycloak_config.json")
			.withEnv("DB_VENDOR", "h2");

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	private OutputDestination output;

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookClient bookClient;

	private static String accessTokenIrma;
	private static String accessTokenSheldon;

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", OrderControllerIntegrationTests::r2dbcUrl);
		registry.add("spring.r2dbc.username", postgresql::getUsername);
		registry.add("spring.r2dbc.password", postgresql::getPassword);

		registry.add("spring.flyway.url", postgresql::getJdbcUrl);
		registry.add("spring.flyway.user", postgresql::getUsername);
		registry.add("spring.flyway.password", postgresql::getPassword);

		registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
				() -> keycloak.getAuthServerUrl() + "/realms/PolarBookshop");
	}

	private static String r2dbcUrl() {
		return String.format("r2dbc:postgresql://%s:%s/%s", postgresql.getHost(),
				postgresql.getFirstMappedPort(), postgresql.getDatabaseName());
	}

	@BeforeAll
	static void accessTokenRequest() throws JsonProcessingException {
		String tokenUrl = keycloak.getAuthServerUrl() + "/realms/PolarBookshop/protocol/openid-connect/token";
		accessTokenIrma = getAccessToken(tokenUrl, "irma.pince");
		accessTokenSheldon = getAccessToken(tokenUrl, "sheldon.cooper");
	}

	@Test
	void whenGetOwnOrdersThenReturn() throws IOException {
		String bookIsbn = "1234567893";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.just(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 1);

		Order expectedOrder = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(expectedOrder).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(expectedOrder.getId()));

		webTestClient.get().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Order.class)
				.value(orders -> {
					List<Long> orderIds = orders.stream()
							.map(Order::getId)
							.collect(Collectors.toList());
					assertThat(orderIds).contains(expectedOrder.getId());
				});
	}

	@Test
	void whenGetOrdersForAnotherUserThenNotReturned() throws IOException {
		String bookIsbn = "1234567893";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.just(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 1);

		Order orderByIrma = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(orderByIrma).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(orderByIrma.getId()));

		Order orderBySheldon = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenSheldon))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(orderBySheldon).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(orderBySheldon.getId()));

		webTestClient.get().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Order.class)
				.value(orders -> {
					List<Long> orderIds = orders.stream()
							.map(Order::getId)
							.collect(Collectors.toList());
					assertThat(orderIds).contains(orderByIrma.getId());
					assertThat(orderIds).doesNotContain(orderBySheldon.getId());
				});
	}

	@Test
	void whenPostRequestAndBookExistsThenOrderAccepted() throws IOException {
		String bookIsbn = "1234567899";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.just(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		Order createdOrder = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class)
				.value(order -> {
					assertThat(order.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
					assertThat(order.getQuantity()).isEqualTo(orderRequest.getQuantity());
					assertThat(order.getBookName()).isEqualTo(book.getTitle() + " - " + book.getAuthor());
					assertThat(order.getBookPrice()).isEqualTo(book.getPrice());
					assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
				})
				.returnResult().getResponseBody();

		assertThat(createdOrder).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(createdOrder.getId()));
	}

	@Test
	void whenPostRequestAndBookNotExistsThenOrderRejected() {
		String bookIsbn = "1234567894";
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.empty());
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		String test = "{\"keys\":[{"
				+ "\"kid\":\"ckb609q8XD8bLpef1wUyDhawa4XWr_F2z9Zct8OiDeI\","
				+ "\"kty\":\"RSA\","
				+ "\"alg\":\"RS256\","
				+ "\"use\":\"sig\","
				+ "\"n\":\"j0_U50H6oK-7HVt6PiANCKtNF6ednuSj8QiJHwU36a80d9VsEMNiePhlWHlq3OCeI2uuHbiJ_2zvh6T8tJEGvCY1ohrmY7-bAgNoFROORgPRxwBTZx0GHXJ04qupdhOAXorMXav177mTC9KgTZVOVasUutGbsDbagqCoBlhBkIppemjZZgMakjI9NkJxUylqh__HMDCL9AdKvhdPgR-4Op4wzNP739qgfDGiu18xtOujCqOo8pmEvfLmsgI_q4KmKk7rtP_5LpQPNaVbkmt9eNg7r0VKe4vFNx2PwXI1wh4obhG58nfEfepR4Nv4yEHzV3cqBnYx5OJOQJ-SBJhtUQ\","
				+ "\"e\":\"AQAB\","
				+ "\"x5c\":[\"MIICpzCCAY8CBgF5HmsGHTANBgkqhkiG9w0BAQsFADAXMRUwEwYDVQQDDAxTZWN1cml0eURlbW8wHhcNMjEwNDI5MTYxNTAwWhcNMzEwNDI5MTYxNjQwWjAXMRUwEwYDVQQDDAxTZWN1cml0eURlbW8wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCPT9TnQfqgr7sdW3o+IA0Iq00Xp52e5KPxCIkfBTfprzR31WwQw2J4+GVYeWrc4J4ja64duIn/bO+HpPy0kQa8JjWiGuZjv5sCA2gVE45GA9HHAFNnHQYdcnTiq6l2E4Beisxdq/XvuZML0qBNlU5VqxS60ZuwNtqCoKgGWEGQiml6aNlmAxqSMj02QnFTKWqH/8cwMIv0B0q+F0+BH7g6njDM0/vf2qB8MaK7XzG066MKo6jymYS98uayAj+rgqYqTuu0//kulA81pVuSa3142DuvRUp7i8U3HY/BcjXCHihuEbnyd8R96lHg2/jIQfNXdyoGdjHk4k5An5IEmG1RAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGuF9MOBKgBd/ZLa7uRie8VArR0lquVpajq4cQF0z5cw49L8CZvZt3BLR2hb9jUkzWqU2WUYcu3mceGhXPUxBASpMH3hLzgJ5z8Qbs0kIxBGYw6gRYWtn1jDsfudHTD42jQEimc8L/hsTaDwIgClweUm9H5ZFvlgh9cszmS7+moVllaky0skVhQi/kjvj26ijo5cYmXPq+/u7YDm8hV9ce+CNv6DkvkoILbwVRKgbPA+SHEBWz9PkZax5yWNLylll7GX+C9qHCZ+Jjzah+MZUNpnnaKwAyZ+TuJaM+Rz8ykb5zezayiMa7Z3Ch+b85u19ZT4j5qMUe26nm7gSwgALZI=\"],"
				+ "\"x5t\":\"Fivw04QNwt2jEd2slSxrnMAwdyw\","
				+ "\"x5t#S256\":\"TRTPvrwELEV6i3PkO7y8ePKsHJ30-oAyi9O4RwqonHY\"}]}";

		webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(accessTokenIrma))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class)
				.value(order -> {
					assertThat(order.getBookIsbn()).isEqualTo(orderRequest.getIsbn());
					assertThat(order.getQuantity()).isEqualTo(orderRequest.getQuantity());
					assertThat(order.getStatus()).isEqualTo(OrderStatus.REJECTED);
				});
	}

	private static String getAccessToken(String tokenUrl, String username) throws JsonProcessingException {
		WebClient webClient = WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
				.build();

		MultiValueMap<String, String> oAuthParameters = new LinkedMultiValueMap<>();
		oAuthParameters.put(OAuth2ParameterNames.GRANT_TYPE, List.of("password"));
		oAuthParameters.put(OAuth2ParameterNames.CLIENT_ID, List.of("edge-service"));
		oAuthParameters.put(OAuth2ParameterNames.CLIENT_SECRET, List.of("6c8521c5-7e70-41c0-a868-0d60b88e463b"));
		oAuthParameters.put(OAuth2ParameterNames.USERNAME, List.of(username));
		oAuthParameters.put(OAuth2ParameterNames.PASSWORD, List.of("password"));

		String accessTokenResponse = webClient.post().uri(tokenUrl)
				.bodyValue(oAuthParameters)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		return new ObjectMapper().readValue(accessTokenResponse, ObjectNode.class)
				.get(OAuth2ParameterNames.ACCESS_TOKEN).asText();
	}
}
