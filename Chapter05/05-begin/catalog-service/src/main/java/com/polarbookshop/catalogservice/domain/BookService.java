package com.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

	public Book editBookDetails(String isbn, Book book) {
		return bookRepository.findByIsbn(isbn)
				.map(existingBook -> {
					var bookToUpdate = Book.builder()
                            .id(existingBook.id())
                            .isbn(book.isbn())
                            .version(existingBook.version()) // 确保 version 没有变化
                            .title(book.title())
                            .author(book.author())
                            .price(book.price())
                            .createdDate(existingBook.createdDate()) // 无变化
                            .build();
					return bookRepository.save(bookToUpdate);
				})
				.orElseGet(() -> addBookToCatalog(book));
	}

}
