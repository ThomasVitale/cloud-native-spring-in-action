package com.polarbookshop.catalogservice.demo;

import java.time.Year;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("test-data")
@RequiredArgsConstructor
public class BookDataLoader {

	private final BookRepository bookRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void loadBookTestData() {
		Book book1 = new Book("1234567891", "Northern Lights", "Lyra Silvertongue", Year.of(2011));
		Book book2 = new Book("1234567892", "Polar Journey", "Iorek Polarson", Year.of(1993));
		bookRepository.save(book1);
		bookRepository.save(book2);
	}
}
