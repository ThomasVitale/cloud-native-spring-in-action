package com.polarbookshop.catalogservice.persistence;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    static {
        Book book = new Book("1234567891", "Northern Lights", "Lyra Silvertongue", Year.of(2001));
        books.put(book.getIsbn(), book);
    }

    @Override
    public Collection<Book> findAllOrderByTitle() {
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
        books.put(book.getIsbn(), book);
        return book;
    }

    @Override
    public Book delete(Book book) {
        return books.remove(book.getIsbn());
    }

    @Override
    public Book update(String isbn, Book book) {
        books.put(isbn, book);
        return book;
    }
}
