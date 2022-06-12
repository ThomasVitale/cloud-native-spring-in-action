package com.polarbookshop.quotefunction.functions;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.polarbookshop.quotefunction.domain.Genre;
import com.polarbookshop.quotefunction.domain.Quote;
import com.polarbookshop.quotefunction.domain.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuoteFunctions {

	private static final Logger log = LoggerFactory.getLogger(QuoteFunctions.class);

	@Bean
	Supplier<Flux<Quote>> allQuotes(QuoteService quoteService) {
		return () -> {
			log.info("Getting all quotes");
			return quoteService.getAllQuotes()
					.delaySequence(Duration.ofSeconds(1));
		};
	}

	@Bean
	Supplier<Mono<Quote>> randomQuote(QuoteService quoteService) {
		return () -> {
			log.info("Getting random quote");
			return quoteService.getRandomQuote();
		};
	}

	@Bean
	Function<Mono<Genre>, Mono<Quote>> genreQuote(QuoteService quoteService) {
		return mono -> mono.flatMap(genre -> {
			log.info("Getting quote for type {}", genre);
			return quoteService.getRandomQuoteByGenre(genre);
		});
	}

	@Bean
	Consumer<Quote> logQuote() {
		return quote -> log.info("Quote: '{}' by {}", quote.content(), quote.author());
	}

}
