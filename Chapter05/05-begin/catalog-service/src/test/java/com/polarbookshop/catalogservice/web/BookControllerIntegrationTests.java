package com.polarbookshop.catalogservice.web;

import java.time.Year;
import java.util.Objects;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        String bookIsbn = "1231231230";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90);
        Book expectedBook = restTemplate.postForEntity("/books", bookToCreate, Book.class).getBody();

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void whenPostRequestThenBookCreated() {
        Book expectedBook = new Book("1231231231", "Title", "Author", Year.of(1991), 9.90);

        ResponseEntity<Book> response = restTemplate.postForEntity("/books", expectedBook, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIsbn()).isEqualTo(expectedBook.getIsbn());
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        String bookIsbn = "1231231232";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90);
        Book createdBook = restTemplate.postForEntity("/books", bookToCreate, Book.class).getBody();
        Objects.requireNonNull(createdBook).setPublishingYear(Year.of(1990));

        restTemplate.put("/books/" + bookIsbn, createdBook);

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPublishingYear()).isEqualTo(Year.of(1990));
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        String bookIsbn = "1231231233";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1973), 9.90);
        restTemplate.postForEntity("/books", bookToCreate, Book.class);

        restTemplate.delete("/books/" + bookIsbn);

        ResponseEntity<String> response = restTemplate.getForEntity("/books/" + bookIsbn, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("The book with ISBN " + bookIsbn + " was not found.");
    }
}
