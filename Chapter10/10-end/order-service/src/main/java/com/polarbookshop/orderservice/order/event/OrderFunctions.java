package com.polarbookshop.orderservice.order.event;

import java.util.function.Consumer;

import com.polarbookshop.orderservice.order.domain.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderFunctions {

	private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

	@Bean
	public Consumer<Flux<OrderDispatchedMessage>> dispatchOrder(OrderService orderService) {
		return flux -> orderService.updateDispatchedOrder(flux)
				.doOnNext(order -> log.info("The order with id {} has been dispatched", order.id()))
				.subscribe();
	}

}
