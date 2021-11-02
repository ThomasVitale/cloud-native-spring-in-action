package com.polarbookshop.catalogservice.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book,Long> {

	Optional<Book> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);

	@Transactional
	void deleteByIsbn(String isbn);

}
