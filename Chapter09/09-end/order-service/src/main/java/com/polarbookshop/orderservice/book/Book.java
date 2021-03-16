package com.polarbookshop.orderservice.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	private String isbn;
	private String title;
	private String author;
	private Double price;
}
