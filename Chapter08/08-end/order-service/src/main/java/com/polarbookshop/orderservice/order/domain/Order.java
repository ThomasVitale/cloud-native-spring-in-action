package com.polarbookshop.orderservice.order.domain;

import java.time.Instant;

import com.polarbookshop.orderservice.book.Book;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public record Order (

	@Id
	Long id,

	String bookIsbn,
	String bookName,
	Double bookPrice,
	Integer quantity,
	OrderStatus status,

	@CreatedDate
	Instant createdDate,

	@LastModifiedDate
	Instant lastModifiedDate,

	@Version
	Integer version
){

	public static Order buildRejectedOrder(String bookIsbn, int quantity) {
		return new Order(null, bookIsbn, null, null, quantity,
				OrderStatus.REJECTED, null, null, null);
	}

	public static Order buildAcceptedOrder(Book book, int quantity) {
		return new Order(null, book.isbn(), book.title() + " - " + book.author(),
				book.price(), quantity, OrderStatus.ACCEPTED, null, null, null);
	}

}
