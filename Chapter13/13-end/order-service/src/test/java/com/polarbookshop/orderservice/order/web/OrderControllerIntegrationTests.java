package com.polarbookshop.orderservice.order.web;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import com.polarbookshop.orderservice.order.event.OrderAcceptedMessage;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestChannelBinderConfiguration.class)
@AutoConfigureWireMock(port = 0)
@Testcontainers
class OrderControllerIntegrationTests {

	private static final String ACCESS_TOKEN_BJORN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImJqb3JuIiwicm9sZXMiOlsiY3VzdG9tZXIiXSwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE4OTM0NTYwMDB9.L1Gqb8Lp1e0fmc3alxrTcWKS54mMjnySc3WWZXrxo5d4EEk7jwE0B71OgQuX2KJHNF_E413hcTTIaYU1ZTNY3qh32xo0ZhGyr8bOHpVRaVpGMojxzPWzsuUCO1eNAbMH6WQVEAgDX97kguGO87Sy7xlLVm5ZamO8CHQxBYhEqRGwpjGzD9Sn81leaVBnQ-Dv3n0aTjoD2B1MW0nVBifRhhcnRg2Mj8cqf93Hud_4f2ZWkigmLpdUcLStlU8WDqTSMBxscubjUWhW44WbEiOwCbj1mUZgI5ZBh67t3_TLvb4te8C2MHJMv8Vg5iCdBDbq7IYUh9ki4FPMsjniX0f8bg";

	private static final String ACCESS_TOKEN_ISABELLE = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImlzYWJlbGxlIiwicm9sZXMiOlsiY3VzdG9tZXIiLCJlbXBsb3llZSJdLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTg5MzQ1NjAwMH0.OI4v23t4dXgIBwPanrMdzPLbd3c7-gAzbhboi1-H-wlUkJ6cnZPRIuFRqsVgMcEmkFm_PPKbZOb16SZcr4EdRaFUNbl86MJ4HzhCcV-RenLflQWN9-t0jIvPPO44HsVJ3ZsUJfz2W3MwbC8BO3oROD3bMwTSVYG8HXN1fOzCXV3ssBEmDWGf-BfdMoKvjTX-rngFcS7qgRIybJ7wKBRyjn0mXfCrbOUZzdGEFhIzUwHWcWmiHGs-R3WlLnME2HxrboAM4tM4uleCsZA-JNO7MRKlLoJF6AD7U9uPDqlSmqSpBTJRojQrblNOIv72UeAJMthLTuF8meAe0J5TLfMhKw";

	@Container
	static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"));

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	private OutputDestination output;

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookClient bookClient;

	@DynamicPropertySource
	@SuppressWarnings("unused")
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", OrderControllerIntegrationTests::r2dbcUrl);
		registry.add("spring.r2dbc.username", postgresql::getUsername);
		registry.add("spring.r2dbc.password", postgresql::getPassword);

		registry.add("spring.flyway.url", postgresql::getJdbcUrl);
		registry.add("spring.flyway.user", postgresql::getUsername);
		registry.add("spring.flyway.password", postgresql::getPassword);

		registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
				() -> "http://localhost:${wiremock.server.port}/protocol/openid-connect/certs");
	}

	private static String r2dbcUrl() {
		return String.format("r2dbc:postgresql://%s:%s/%s", postgresql.getHost(),
				postgresql.getFirstMappedPort(), postgresql.getDatabaseName());
	}

	@Test
	void whenGetOwnOrdersThenReturn() throws IOException {
		String bookIsbn = "1234567893";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.just(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 1);

		Order expectedOrder = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(expectedOrder).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(expectedOrder.getId()));

		webTestClient.get().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
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

		Order orderByBjorn = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(orderByBjorn).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(orderByBjorn.getId()));

		Order orderByIsabelle = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_ISABELLE))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class).returnResult().getResponseBody();
		assertThat(orderByIsabelle).isNotNull();
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderAcceptedMessage.class))
				.isEqualTo(new OrderAcceptedMessage(orderByIsabelle.getId()));

		webTestClient.get().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Order.class)
				.value(orders -> {
					List<Long> orderIds = orders.stream()
							.map(Order::getId)
							.collect(Collectors.toList());
					assertThat(orderIds).contains(orderByBjorn.getId());
					assertThat(orderIds).doesNotContain(orderByIsabelle.getId());
				});
	}

	@Test
	void whenPostRequestAndBookExistsThenOrderAccepted() throws IOException {
		String bookIsbn = "1234567899";
		Book book = new Book(bookIsbn, "Title", "Author", 9.90);
		given(bookClient.getBookByIsbn(bookIsbn)).willReturn(Mono.just(book));
		OrderRequest orderRequest = new OrderRequest(bookIsbn, 3);

		Order createdOrder = webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class)
				.value(order -> {
					assertThat(order.getBookIsbn()).isEqualTo(orderRequest.isbn());
					assertThat(order.getQuantity()).isEqualTo(orderRequest.quantity());
					assertThat(order.getBookName()).isEqualTo(book.title() + " - " + book.author());
					assertThat(order.getBookPrice()).isEqualTo(book.price());
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

		webTestClient.post().uri("/orders")
				.headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_BJORN))
				.bodyValue(orderRequest)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Order.class)
				.value(order -> {
					assertThat(order.getBookIsbn()).isEqualTo(orderRequest.isbn());
					assertThat(order.getQuantity()).isEqualTo(orderRequest.quantity());
					assertThat(order.getStatus()).isEqualTo(OrderStatus.REJECTED);
				});
	}

}
