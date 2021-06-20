package com.polarbookshop.orderservice.order.web;

import com.polarbookshop.orderservice.order.domain.Order;
import com.polarbookshop.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class OrderJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<Order> json;

    @Test
    void testSerialize() throws Exception {
        Order order = new Order("1234567890", "Book Name", 9.90, 1, OrderStatus.ACCEPTED);
        assertThat(json.write(order)).extractingJsonPathStringValue("@.bookIsbn")
                .isEqualTo("1234567890");
        assertThat(json.write(order)).extractingJsonPathStringValue("@.bookName")
                .isEqualTo("Book Name");
        assertThat(json.write(order)).extractingJsonPathNumberValue("@.bookPrice")
                .isEqualTo(9.90);
        assertThat(json.write(order)).extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(1);
        assertThat(json.write(order)).extractingJsonPathStringValue("@.status")
                .isEqualTo("ACCEPTED");
    }
}
