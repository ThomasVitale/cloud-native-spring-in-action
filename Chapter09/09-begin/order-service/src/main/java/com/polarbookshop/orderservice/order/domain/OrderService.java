package com.polarbookshop.orderservice.order.domain;

import com.polarbookshop.orderservice.book.Book;
import com.polarbookshop.orderservice.book.BookClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

	private final BookClient bookClient;
	private final OrderRepository orderRepository;

	public OrderService(BookClient bookClient, OrderRepository orderRepository) {
		this.bookClient = bookClient;
		this.orderRepository = orderRepository;
	}

	public Flux<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Mono<Order> submitOrder(String isbn, int quantity) {
		return bookClient.getBookByIsbn(isbn)
				.flatMap(book -> Mono.just(buildAcceptedOrder(book, quantity)))
				.defaultIfEmpty(buildRejectedOrder(isbn, quantity))
				.flatMap(orderRepository::save);
	}

	private Order buildAcceptedOrder(Book book, int quantity) {
		return new Order(book.isbn(),
				book.title() + " - " + book.author(),
				book.price(),
				quantity,
				OrderStatus.ACCEPTED);
	}

	private Order buildRejectedOrder(String isbn, int quantity) {
		return new Order(isbn, quantity, OrderStatus.REJECTED);
	}

}
