package com.polarbookshop.catalogservice.domain;

import java.util.List;
import java.util.Optional;

import com.polarbookshop.catalogservice.persistence.DataConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findAllBooks() {
        var book1 = new Book(null, "1234561235", "Title", "Author", 12.90, "Polarsophia", null, null, null);
        var book2 = new Book(null, "1234561236", "Another Title", "Author", 12.90, "Polarsophia", null, null, null);
        var expectedBook1 = jdbcAggregateTemplate.insert(book1);
        var expectedBook2 = jdbcAggregateTemplate.insert(book2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        assertThat(actualBooks).asList().containsAll(List.of(expectedBook1, expectedBook2));
    }

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561235";
        var book = new Book(null, bookIsbn, "Title", "Author", 12.90, "Polarsophia", null, null, null);
        var expectedBook = jdbcAggregateTemplate.insert(book);

        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepository.findByIsbn("1234561236");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        var bookIsbn = "1234561235";
        var bookToCreate = new Book(null, bookIsbn, "Title", "Author", 12.90, "Polarsophia", null, null, null);
        jdbcAggregateTemplate.insert(bookToCreate);

        boolean existing = bookRepository.existsByIsbn(bookIsbn);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByIsbnWhenNotExisting() {
        boolean existing = bookRepository.existsByIsbn("1234561235");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByIsbn() {
        var bookIsbn = "1234561235";
        var bookToCreate = new Book(null, bookIsbn, "Title", "Author", 12.90, "Polarsophia", null, null, null);
        var persistedBook = jdbcAggregateTemplate.insert(bookToCreate);

        bookRepository.deleteByIsbn(bookIsbn);

        assertThat(jdbcAggregateTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }

}