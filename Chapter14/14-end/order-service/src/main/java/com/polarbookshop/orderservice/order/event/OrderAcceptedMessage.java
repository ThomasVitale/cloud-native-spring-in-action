package com.polarbookshop.orderservice.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderAcceptedMessage {
	private Long orderId;
}
