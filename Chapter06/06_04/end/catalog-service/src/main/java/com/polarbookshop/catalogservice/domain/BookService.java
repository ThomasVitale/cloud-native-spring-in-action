package com.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Book removeBookFromCatalog(String isbn) {
        Book bookToRemove = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        bookRepository.deleteByIsbn(isbn);
        return bookToRemove;
    }

    @Transactional
    public Book editBookDetails(String isbn, Book book) {
        Book bookToUpdate = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublishingYear(book.getPublishingYear());
        bookToUpdate.setPrice(book.getPrice());

        return bookRepository.save(bookToUpdate);
    }
}
