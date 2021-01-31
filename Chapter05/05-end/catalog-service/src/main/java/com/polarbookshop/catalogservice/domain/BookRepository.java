package com.polarbookshop.catalogservice.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);
	void deleteByIsbn(String isbn);
}
