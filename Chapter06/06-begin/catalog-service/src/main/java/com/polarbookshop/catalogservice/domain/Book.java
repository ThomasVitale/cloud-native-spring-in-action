package com.polarbookshop.catalogservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.polarbookshop.catalogservice.persistence.AuditMetadata;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;

public record Book (

        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must follow the standards ISBN-10 or ISBN-13.")
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        String publisher,

        @Embedded.Empty
        AuditMetadata auditMetadata,

        @Version
        int version

){

        public static Book build(String isbn, String title, String author, Double price, String publisher) {
                return new Book(null, isbn, title, author, price, publisher, new AuditMetadata(null, null), 0);
        }

}
