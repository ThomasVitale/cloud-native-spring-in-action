package com.polarbookshop.orderservice.order.event;

import java.util.function.Consumer;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderService;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OrderFunctions {

	@Bean
	public Consumer<Order> publishOrderAcceptedEvent(StreamBridge streamBridge) {
		return order -> {
			if (!order.getStatus().equals(OrderStatus.ACCEPTED)) {
				return;
			}
			OrderAcceptedMessage orderAcceptedMessage = new OrderAcceptedMessage(order.getId());
			log.info("Sending order accepted event with id: " + order.getId());
			streamBridge.send("order-accepted", orderAcceptedMessage);
		};
	}

	@Bean
	public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
		return orderDispatchedMessage -> {
			log.info("The order with id " + orderDispatchedMessage.getOrderId() + " has been dispatched.");
			orderService.updateOrderStatus(orderDispatchedMessage.getOrderId(), OrderStatus.DISPATCHED);
		};
	}
}
