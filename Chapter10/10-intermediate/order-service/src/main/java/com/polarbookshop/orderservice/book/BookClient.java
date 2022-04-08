package com.polarbookshop.orderservice.book;

import java.time.Duration;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class BookClient {

	private static final String BOOKS_ROOT_API = "/books/";
	private final WebClient webClient;

	public BookClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<Book> getBookByIsbn(String isbn) {
		return webClient
				.get()
				.uri(BOOKS_ROOT_API + isbn)
				.retrieve()
				.bodyToMono(Book.class)
				.timeout(Duration.ofSeconds(3), Mono.empty())
				.onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
				.retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
				.onErrorResume(Exception.class, exception -> Mono.empty());
	}

}
