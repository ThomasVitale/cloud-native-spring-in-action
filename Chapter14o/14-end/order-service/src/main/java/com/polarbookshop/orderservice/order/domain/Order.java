package com.polarbookshop.orderservice.order.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

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

	 @CreatedBy
	 String createdBy,

	 @LastModifiedBy
	 String lastModifiedBy,

	 @Version
	 int version
){

	public static Order build(String bookIsbn, String bookName, Double bookPrice, Integer quantity, OrderStatus status) {
		return new Order(null, bookIsbn, bookName, bookPrice, quantity, status, null, null, null, null, 0);
	}

}
