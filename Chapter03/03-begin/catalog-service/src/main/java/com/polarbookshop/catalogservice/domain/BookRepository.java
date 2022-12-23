package com.polarbookshop.catalogservice.domain;

import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface BookRepository {
Iterable<Book> findAll();

  Optional<Book> findByIsbn(String isbn);

  boolean existsByIsbn(String isbn);

  Book save(Book book);

  void deleteByIsbn(String isbn);
}
