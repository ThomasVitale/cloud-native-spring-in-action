package com.polarbookshop.orderservice.order.event;

import java.util.function.Consumer;

import com.polarbookshop.orderservice.order.domain.OrderService;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderFunctions {

	private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

	@Bean
	public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
		return orderDispatchedMessage -> {
			log.info("The order with id {} has been dispatched", orderDispatchedMessage.orderId());
			orderService.updateOrderStatus(orderDispatchedMessage.orderId(), OrderStatus.DISPATCHED);
		};
	}

}

