package com.polarbookshop.catalogservice.domain;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import com.polarbookshop.catalogservice.persistence.JpaConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryJpaTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllOrderByTitle() {
        Book expectedBook1 = new Book("1234561235", "Title", "Author", Year.of(2000), 12.90, "Polar");
        Book expectedBook2 = new Book("1234561236", "Another Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(expectedBook1);
        entityManager.persist(expectedBook2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        assertThat(actualBooks).asList().containsAll(List.of(expectedBook1, expectedBook2));
    }

    @Test
    void findBookByIsbnWhenExisting() {
        String bookIsbn = "1234561235";
        Book expectedBook = new Book(bookIsbn, "Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(expectedBook);

        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().getIsbn()).isEqualTo(expectedBook.getIsbn());
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepository.findByIsbn("1234561236");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        String bookIsbn = "1234561235";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(2000), 12.90, "Polar");
        entityManager.persist(bookToCreate);

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
        String bookIsbn = "1234561235";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(2000), 12.90, "Polar");
        Book persistedBook = entityManager.persist(bookToCreate);

        bookRepository.deleteByIsbn(bookIsbn);

        assertThat(entityManager.find(Book.class, persistedBook.getId())).isNull();
    }
}