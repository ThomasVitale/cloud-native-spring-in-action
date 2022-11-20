package com.polarbookshop.quoteservice.web;

import com.polarbookshop.quoteservice.domain.Genre;
import com.polarbookshop.quoteservice.domain.Quote;
import com.polarbookshop.quoteservice.domain.QuoteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

	private final QuoteService quoteService;

	public QuoteController(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	@GetMapping("/quotes")
	public Flux<Quote> getAllQuotes() {
		return quoteService.getAllQuotes();
	}

	@GetMapping("/quotes/random")
	public Mono<Quote> getRandomQuote() {
		return quoteService.getRandomQuote();
	}

	@GetMapping("/quotes/random/{genre}")
	public Mono<Quote> getRandomQuote(@PathVariable Genre genre) {
		return quoteService.getRandomQuoteByGenre(genre);
	}

}
