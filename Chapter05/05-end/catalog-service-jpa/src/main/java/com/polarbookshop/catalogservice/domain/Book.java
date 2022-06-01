package com.polarbookshop.catalogservice.domain;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "The book ISBN must be defined.")
		@Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
        private String isbn;

        @NotBlank(message = "The book title must be defined.")
        private String title;

        @NotBlank(message = "The book author must be defined.")
        private String author;

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        private Double price;

        private String publisher;

        @CreatedDate
        private Instant createdDate;

        @LastModifiedDate
        private Instant lastModifiedDate;

        @Version
        private int version;

        public Book() {}

        public Book(Long id, String isbn, String title, String author, Double price, String publisher, Instant createdDate, Instant lastModifiedDate, int version) {
                this.id = id;
                this.isbn = isbn;
                this.title = title;
                this.author = author;
                this.price = price;
                this.publisher = publisher;
                this.createdDate = createdDate;
                this.lastModifiedDate = lastModifiedDate;
                this.version = version;
        }

        public static Book of(String isbn, String title, String author, Double price, String publisher) {
                return new Book(null, isbn, title, author, price, publisher, null, null, 0);
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getIsbn() {
                return isbn;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public String getPublisher() {
                return publisher;
        }

        public void setPublisher(String publisher) {
                this.publisher = publisher;
        }

        public Instant getCreatedDate() {
                return createdDate;
        }

        public void setCreatedDate(Instant createdDate) {
                this.createdDate = createdDate;
        }

        public Instant getLastModifiedDate() {
                return lastModifiedDate;
        }

        public void setLastModifiedDate(Instant lastModifiedDate) {
                this.lastModifiedDate = lastModifiedDate;
        }

        public int getVersion() {
                return version;
        }

        public void setVersion(int version) {
                this.version = version;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Book book = (Book) o;
                return version == book.version && id.equals(book.id) && isbn.equals(book.isbn) && title.equals(book.title) && author.equals(book.author) && price.equals(book.price) && Objects.equals(publisher, book.publisher) && createdDate.equals(book.createdDate) && lastModifiedDate.equals(book.lastModifiedDate);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, isbn, title, author, price, publisher, createdDate, lastModifiedDate, version);
        }

}
