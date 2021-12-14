package com.polarbookshop.orderservice.order.event;

public record OrderDispatchedMessage (
		Long orderId
){}
