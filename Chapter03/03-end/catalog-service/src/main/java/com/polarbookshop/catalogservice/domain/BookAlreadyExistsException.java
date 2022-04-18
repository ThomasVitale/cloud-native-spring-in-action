package com.polarbookshop.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {

    private static final String ERROR_MESSAGE =  "A book with ISBN %s already exists.";

    public BookAlreadyExistsException(String isbn) {
        super(ERROR_MESSAGE.formatted(isbn));
    }

}
