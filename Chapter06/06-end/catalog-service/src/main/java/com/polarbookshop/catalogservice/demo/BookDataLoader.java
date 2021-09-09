package com.polarbookshop.catalogservice.demo;

import java.util.List;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("test-data")
public class BookDataLoader {

	private final BookRepository bookRepository;

	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadBookTestData() {
		var book1 = new Book(null, "1234567891", "Northern Lights", "Lyra Silvertongue", 9.90, "Polarsophia", null, null, null);
		var book2 = new Book(null, "1234567892", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia", null, null, null);
		bookRepository.saveAll(List.of(book1, book2));
	}

}
