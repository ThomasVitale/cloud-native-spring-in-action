package com.polarbookshop.quotefunction;

import com.polarbookshop.quotefunction.domain.Genre;
import com.polarbookshop.quotefunction.domain.Quote;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteFunctionApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	void whenAllQuotesThenReturn() {
		webTestClient
				.get()
				.uri("/")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Quote.class);
	}

	@Test
	void whenRandomQuoteThenReturn() {
		webTestClient
				.get()
				.uri("/randomQuote")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Quote.class);
	}

	@Test
	void whenGenreQuoteThenReturn() {
		webTestClient
				.post()
				.uri("/genreQuote")
				.bodyValue("FANTASY")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Quote.class)
				.value(quote -> assertThat(quote.genre()).isEqualTo(Genre.FANTASY));
	}

	@Test
	void whenGenreQuoteLogQuoteThenReturn() {
		webTestClient
				.post()
				.uri("/genreQuote,logQuote")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("FANTASY")
				.exchange()
				.expectStatus().is2xxSuccessful();
	}

}
