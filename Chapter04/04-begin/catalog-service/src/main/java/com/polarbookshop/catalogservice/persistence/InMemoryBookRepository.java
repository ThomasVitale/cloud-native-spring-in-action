package com.polarbookshop.catalogservice.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookRepository implements BookRepository {

	private static final Map<String, Book> books = new ConcurrentHashMap<>();

	@Override
	public Iterable<Book> findAll() {
		return books.values();
	}

	@Override
	public Optional<Book> findByIsbn(String isbn) {
		return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) : Optional.empty();
	}

	@Override
	public boolean existsByIsbn(String isbn) {
		return books.get(isbn) != null;
	}

	@Override
	public Book save(Book book) {
		books.put(book.isbn(), book);
		return book;
	}

	@Override
	public void deleteByIsbn(String isbn) {
		books.remove(isbn);
	}

}
