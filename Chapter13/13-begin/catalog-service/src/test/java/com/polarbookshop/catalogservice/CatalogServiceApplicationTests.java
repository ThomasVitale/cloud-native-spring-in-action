package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureWireMock(port = 0)
class CatalogServiceApplicationTests {

    // <header>.<payload>.<signature>
    private static final String ACCESS_TOKEN_CUSTOMER = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImJqb3JuIiwicm9sZXMiOlsiY3VzdG9tZXIiXSwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE4OTM0NTYwMDB9.L1Gqb8Lp1e0fmc3alxrTcWKS54mMjnySc3WWZXrxo5d4EEk7jwE0B71OgQuX2KJHNF_E413hcTTIaYU1ZTNY3qh32xo0ZhGyr8bOHpVRaVpGMojxzPWzsuUCO1eNAbMH6WQVEAgDX97kguGO87Sy7xlLVm5ZamO8CHQxBYhEqRGwpjGzD9Sn81leaVBnQ-Dv3n0aTjoD2B1MW0nVBifRhhcnRg2Mj8cqf93Hud_4f2ZWkigmLpdUcLStlU8WDqTSMBxscubjUWhW44WbEiOwCbj1mUZgI5ZBh67t3_TLvb4te8C2MHJMv8Vg5iCdBDbq7IYUh9ki4FPMsjniX0f8bg";

    // <header>.<payload>.<signature>
    private static final String ACCESS_TOKEN_EMPLOYEE = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjM5NCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvUG9sYXJCb29rc2hvcCIsInN1YiI6ImlzYWJlbGxlIiwicm9sZXMiOlsiY3VzdG9tZXIiLCJlbXBsb3llZSJdLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTg5MzQ1NjAwMH0.OI4v23t4dXgIBwPanrMdzPLbd3c7-gAzbhboi1-H-wlUkJ6cnZPRIuFRqsVgMcEmkFm_PPKbZOb16SZcr4EdRaFUNbl86MJ4HzhCcV-RenLflQWN9-t0jIvPPO44HsVJ3ZsUJfz2W3MwbC8BO3oROD3bMwTSVYG8HXN1fOzCXV3ssBEmDWGf-BfdMoKvjTX-rngFcS7qgRIybJ7wKBRyjn0mXfCrbOUZzdGEFhIzUwHWcWmiHGs-R3WlLnME2HxrboAM4tM4uleCsZA-JNO7MRKlLoJF6AD7U9uPDqlSmqSpBTJRojQrblNOIv72UeAJMthLTuF8meAe0J5TLfMhKw";

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    @SuppressWarnings("unused")
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> "http://localhost:${wiremock.server.port}/protocol/openid-connect/certs");
    }

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        var bookIsbn = "1231231230";
        var bookToCreate = new Book(null, bookIsbn, "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book(null, "1231231231", "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);

        webTestClient
                .post()
                .uri("/books")
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPostRequestUnauthenticatedThen401() {
        var expectedBook = new Book(null, "1231231231", "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenPostRequestUnauthorizedThen403() {
        var expectedBook = new Book(null, "1231231231", "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);

        webTestClient
                .post()
                .uri("/books")
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_CUSTOMER))
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        var bookIsbn = "1231231232";
        var bookToCreate = new Book(null, bookIsbn, "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();
        var bookToUpdate = new Book(createdBook.id(), createdBook.isbn(), createdBook.title(), createdBook.author(), 7.95,
                createdBook.publisher(), createdBook.createdDate(), createdBook.lastModifiedDate(),
                createdBook.createdBy(), createdBook.lastModifiedBy(), createdBook.version());

        webTestClient
                .put()
                .uri("/books/" + bookIsbn)
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        var bookIsbn = "1231231233";
        var bookToCreate = new Book(null, bookIsbn, "Title", "Author", 9.90, "Polarsophia", null, null, null, null, null);
        webTestClient
                .post()
                .uri("/books")
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + bookIsbn)
                .headers(headers -> headers.setBearerAuth(ACCESS_TOKEN_EMPLOYEE))
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + bookIsbn)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errorMessage ->
                        assertThat(errorMessage).isEqualTo("The book with ISBN " + bookIsbn + " was not found.")
                );
    }

}
