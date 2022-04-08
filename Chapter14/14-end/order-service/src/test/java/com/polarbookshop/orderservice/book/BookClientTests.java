package com.polarbookshop.orderservice.book;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@TestMethodOrder(MethodOrderer.Random.class)
class BookClientTests {

	private MockWebServer mockWebServer;
	private BookClient bookClient;

	@BeforeEach
	void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.start();

		var webClient = WebClient.builder()
				.baseUrl(mockWebServer.url("/").uri().toString())
				.build();
		this.bookClient = new BookClient(webClient);
	}

	@AfterEach
	void clean() throws IOException {
		this.mockWebServer.shutdown();
	}

	@Test
	void whenBookExistsThenReturnBook() {
		var bookIsbn = "1234567890";

		var mockResponse = new MockResponse()
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setBody("""
							{
								"isbn": %s,
								"title": "Title",
								"author": "Author",
								"price": 9.90,
								"publisher": "Polarsophia"
							}
						""".formatted(bookIsbn));

		mockWebServer.enqueue(mockResponse);

		Mono<Book> book = bookClient.getBookByIsbn(bookIsbn);

		StepVerifier.create(book)
				.expectNextMatches(b -> b.isbn().equals(bookIsbn))
				.verifyComplete();
	}

	@Test
	void whenBookNotExistsThenReturnEmpty() {
		var bookIsbn = "1234567891";

		var mockResponse = new MockResponse()
				.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.setResponseCode(404);

		mockWebServer.enqueue(mockResponse);

		StepVerifier.create(bookClient.getBookByIsbn(bookIsbn))
				.expectNextCount(0)
				.verifyComplete();
	}

}