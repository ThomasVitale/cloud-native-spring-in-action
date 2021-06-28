package com.polarbookshop.catalogservice.web;

import java.time.Year;
import java.util.Objects;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureWireMock(port = 0)
class BookControllerIntegrationTests {

    // <header>.<payload>.<signature>
    private static final String ACCESS_TOKEN_CUSTOMER = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImJqb3JuIiwicm9sZXMiOlsiY3VzdG9tZXIiXSwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE4OTM0NTYwMDB9.L1Gqb8Lp1e0fmc3alxrTcWKS54mMjnySc3WWZXrxo5d4EEk7jwE0B71OgQuX2KJHNF_E413hcTTIaYU1ZTNY3qh32xo0ZhGyr8bOHpVRaVpGMojxzPWzsuUCO1eNAbMH6WQVEAgDX97kguGO87Sy7xlLVm5ZamO8CHQxBYhEqRGwpjGzD9Sn81leaVBnQ-Dv3n0aTjoD2B1MW0nVBifRhhcnRg2Mj8cqf93Hud_4f2ZWkigmLpdUcLStlU8WDqTSMBxscubjUWhW44WbEiOwCbj1mUZgI5ZBh67t3_TLvb4te8C2MHJMv8Vg5iCdBDbq7IYUh9ki4FPMsjniX0f8bg";

    // <header>.<payload>.<signature>
    private static final String ACCESS_TOKEN_EMPLOYEE = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImlzYWJlbGxlIiwicm9sZXMiOlsiY3VzdG9tZXIiLCJlbXBsb3llZSJdLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTg5MzQ1NjAwMH0.OI4v23t4dXgIBwPanrMdzPLbd3c7-gAzbhboi1-H-wlUkJ6cnZPRIuFRqsVgMcEmkFm_PPKbZOb16SZcr4EdRaFUNbl86MJ4HzhCcV-RenLflQWN9-t0jIvPPO44HsVJ3ZsUJfz2W3MwbC8BO3oROD3bMwTSVYG8HXN1fOzCXV3ssBEmDWGf-BfdMoKvjTX-rngFcS7qgRIybJ7wKBRyjn0mXfCrbOUZzdGEFhIzUwHWcWmiHGs-R3WlLnME2HxrboAM4tM4uleCsZA-JNO7MRKlLoJF6AD7U9uPDqlSmqSpBTJRojQrblNOIv72UeAJMthLTuF8meAe0J5TLfMhKw";

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    @SuppressWarnings("unused")
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> "http://localhost:${wiremock.server.port}/protocol/openid-connect/certs");
    }

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        String bookIsbn = "1231231230";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Book expectedBook = restTemplate.postForEntity("/books", new HttpEntity<>(bookToCreate, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)), Book.class).getBody();

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void whenPostRequestThenBookCreated() {
        Book expectedBook = new Book("1231231231", "Title", "Author", Year.of(1991), 9.90, "Polar");

        ResponseEntity<Book> response = restTemplate
                .postForEntity("/books", new HttpEntity<>(expectedBook, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)), Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIsbn()).isEqualTo(expectedBook.getIsbn());
    }

    @Test
    void whenPostRequestUnauthenticatedThen401() {
        Book expectedBook = new Book("1231231231", "Title", "Author", Year.of(1991), 9.90, "Polar");

        ResponseEntity<Book> response = restTemplate
                .postForEntity("/books", expectedBook, Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void whenPostRequestUnauthorizedThen403() {
        Book expectedBook = new Book("1231231231", "Title", "Author", Year.of(1991), 9.90, "Polar");

        ResponseEntity<Book> response = restTemplate
                .postForEntity("/books", new HttpEntity<>(expectedBook, getAuthorizationBearerHeader(ACCESS_TOKEN_CUSTOMER)), Book.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        String bookIsbn = "1231231232";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Book createdBook = restTemplate
                .postForEntity("/books", new HttpEntity<>(bookToCreate, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)), Book.class).getBody();
        Objects.requireNonNull(createdBook).setPublishingYear(Year.of(1990));

        restTemplate.put("/books/" + bookIsbn, new HttpEntity<>(createdBook, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)));

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPublishingYear()).isEqualTo(Year.of(1990));
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        String bookIsbn = "1231231233";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1973), 9.90, "Polar");
        restTemplate.postForEntity("/books", new HttpEntity<>(bookToCreate, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)), Book.class);

        restTemplate.exchange("/books/" + bookIsbn, HttpMethod.DELETE, new HttpEntity<>(null, getAuthorizationBearerHeader(ACCESS_TOKEN_EMPLOYEE)), Void.class);

        ResponseEntity<String> response = restTemplate.getForEntity("/books/" + bookIsbn, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("The book with ISBN " + bookIsbn + " was not found.");
    }

    private HttpHeaders getAuthorizationBearerHeader(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        return httpHeaders;
    }

}
