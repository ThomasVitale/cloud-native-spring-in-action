package com.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Collection<Book> viewBookList() {
        return bookRepository.findAllOrderByTitle();
    }

    public Optional<Book> viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public Book removeBookFromCatalog(String isbn) {
        Book bookToRemove = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return bookRepository.delete(bookToRemove);
    }

    public Book editBookDetails(String isbn, Book book) {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException(isbn);
        }
        Book bookToUpdate = new Book(isbn, book.getTitle(), book.getAuthor(), book.getPublishingYear());
        return bookRepository.update(isbn, bookToUpdate);
    }
}
