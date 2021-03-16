package com.polarbookshop.orderservice.order.web;

import javax.validation.Valid;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public Flux<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("{id}")
	public Mono<Order> getOrderById(@PathVariable Long id) {
		return orderService.getOrder(id);
	}

	@PostMapping
	public Mono<Order> submitOrder(@RequestBody @Valid OrderRequest orderRequest) {
		return orderService.submitOrder(orderRequest.getIsbn(), orderRequest.getQuantity());
	}
}
