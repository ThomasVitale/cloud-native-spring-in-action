package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        // Given
        String bookIsbn = "1231231230";
        Book expectedBook = new Book(bookIsbn, "Title", "Author", Year.of(1991));
        restTemplate.postForEntity("/books", expectedBook, Book.class);

        // When
        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIsbn()).isEqualTo(bookIsbn);
    }

    @Test
    void whenPostRequestThenBookCreated() {
        // Given
        Book expectedBook = new Book("1231231231", "Title", "Author", Year.of(1991));

        // When
        ResponseEntity<Book> response = restTemplate.postForEntity("/books", expectedBook, Book.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        // Given
        Book expectedBook = new Book("1231231232", "Title", "Author", Year.of(1991));
        ResponseEntity<Book> postResponse = restTemplate.postForEntity("/books", expectedBook, Book.class);
        Book createdBook = postResponse.getBody();
        Objects.requireNonNull(createdBook).setPublishingYear(Year.of(1990));

        // When
        restTemplate.put("/books/1231231232", createdBook);

        // Then
        ResponseEntity<Book> response = restTemplate.getForEntity("/books/1231231232", Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPublishingYear()).isEqualTo(Year.of(1990));
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        // Given
        String bookIsbn = "1231231233";
        Book expectedBook = new Book(bookIsbn, "Title", "Author", Year.of(1973));
        restTemplate.postForEntity("/books", expectedBook, Book.class);

        // When
        restTemplate.delete("/books/" + bookIsbn);

        // Then
        ResponseEntity<String> response = restTemplate.getForEntity("/books/" + bookIsbn, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("The book with ISBN " + bookIsbn + " was not found.");
    }
}
