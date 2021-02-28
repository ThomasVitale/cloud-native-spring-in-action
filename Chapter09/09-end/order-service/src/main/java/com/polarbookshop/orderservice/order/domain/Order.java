package com.polarbookshop.orderservice.order.domain;

import com.polarbookshop.orderservice.order.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
@Data @AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends PersistableEntity {

	private String bookIsbn;
	private String bookName;
	private Double bookPrice;
	private Integer quantity;
	private OrderStatus status;

	public Order(String bookIsbn, int quantity, OrderStatus status) {
		this.bookIsbn = bookIsbn;
		this.quantity = quantity;
		this.status = status;
	}
}
