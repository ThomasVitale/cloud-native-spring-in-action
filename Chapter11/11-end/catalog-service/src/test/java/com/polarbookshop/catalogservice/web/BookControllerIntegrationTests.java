package com.polarbookshop.catalogservice.web;

import java.time.Year;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polarbookshop.catalogservice.domain.Book;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@Testcontainers
class BookControllerIntegrationTests {

    static HttpHeaders authorizationBearerHeader;

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer("jboss/keycloak:12.0.4")
                    .withRealmImportFile("keycloak_config.json")
                    .withEnv("DB_VENDOR", "h2");

    @DynamicPropertySource
    static void jwtValidationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/PolarBookshop");
    }

    @BeforeAll
    static void accessTokenRequest() {
        String tokenUrl = keycloak.getAuthServerUrl() + "/realms/PolarBookshop/protocol/openid-connect/token";
        authorizationBearerHeader = getAuthorizationBearerHeader(tokenUrl);
    }

    @Test
    void whenGetRequestWithIdThenBookReturned() {
        String bookIsbn = "1231231230";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Book expectedBook = restTemplate.postForEntity("/books", new HttpEntity<>(bookToCreate, authorizationBearerHeader), Book.class).getBody();
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
                .postForEntity("/books", new HttpEntity<>(expectedBook, authorizationBearerHeader), Book.class);

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
    void whenPutRequestThenBookUpdated() {
        String bookIsbn = "1231231232";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1991), 9.90, "Polar");
        Book createdBook = restTemplate
                .postForEntity("/books", new HttpEntity<>(bookToCreate, authorizationBearerHeader), Book.class).getBody();
        Objects.requireNonNull(createdBook).setPublishingYear(Year.of(1990));

        restTemplate.put("/books/" + bookIsbn, new HttpEntity<>(createdBook, authorizationBearerHeader));

        ResponseEntity<Book> response = restTemplate.getForEntity("/books/" + bookIsbn, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPublishingYear()).isEqualTo(Year.of(1990));
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {
        String bookIsbn = "1231231233";
        Book bookToCreate = new Book(bookIsbn, "Title", "Author", Year.of(1973), 9.90, "Polar");
        restTemplate.postForEntity("/books", new HttpEntity<>(bookToCreate, authorizationBearerHeader), Book.class);

        restTemplate.exchange("/books/" + bookIsbn, HttpMethod.DELETE, new HttpEntity<>(null, authorizationBearerHeader), Void.class);

        ResponseEntity<String> response = restTemplate.getForEntity("/books/" + bookIsbn, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("The book with ISBN " + bookIsbn + " was not found.");
    }

    private static HttpHeaders getAuthorizationBearerHeader(String tokenUrl) {
        RestTemplate keycloakRest = new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
                .build();

        MultiValueMap<String, String> oAuth2Parameters = new LinkedMultiValueMap<>();
        oAuth2Parameters.put(OAuth2ParameterNames.GRANT_TYPE, List.of("password"));
        oAuth2Parameters.put(OAuth2ParameterNames.CLIENT_ID, List.of("edge-service"));
        oAuth2Parameters.put(OAuth2ParameterNames.CLIENT_SECRET, List.of("6c8521c5-7e70-41c0-a868-0d60b88e463b"));
        oAuth2Parameters.put(OAuth2ParameterNames.USERNAME, List.of("irma.pince"));
        oAuth2Parameters.put(OAuth2ParameterNames.PASSWORD, List.of("password"));

        String accessTokenResponse = keycloakRest.postForObject(tokenUrl, oAuth2Parameters, String.class);
        HttpHeaders authorizedHeaders = new HttpHeaders();
        assertThatNoException().isThrownBy(() -> authorizedHeaders.setBearerAuth(new ObjectMapper()
                .readValue(accessTokenResponse, ObjectNode.class).get(OAuth2ParameterNames.ACCESS_TOKEN).asText()));
        return authorizedHeaders;
    }
}
