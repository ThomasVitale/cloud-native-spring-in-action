package com.polarbookshop.catalogservice.domain;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository {
	Collection<Book> findAllOrderByTitle();
	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);
	Book save(Book book);
	void delete(String isbn);
	Book update(String isbn, Book book);
}
