package com.polarbookshop.dispatcherservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderAcceptedMessage {
	private Long orderId;
}
