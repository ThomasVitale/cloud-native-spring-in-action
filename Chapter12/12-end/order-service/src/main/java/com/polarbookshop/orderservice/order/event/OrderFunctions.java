package com.polarbookshop.orderservice.order.event;

import java.util.function.Consumer;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderService;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderFunctions {

	private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

	@Bean
	public Consumer<Order> publishOrderAcceptedEvent(StreamBridge streamBridge) {
		return order -> {
			if (!order.getStatus().equals(OrderStatus.ACCEPTED)) {
				return;
			}
			OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.getId());
			log.info("Sending order accepted event with id: {}", order.getId());
			streamBridge.send("order-accepted", orderAcceptedMessage);
		};
	}

	@Bean
	public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
		return orderDispatchedMessage -> {
			log.info("The order with id {} has been dispatched", orderDispatchedMessage.orderId());
			orderService.updateOrderStatus(orderDispatchedMessage.orderId(), OrderStatus.DISPATCHED);
		};
	}

}
