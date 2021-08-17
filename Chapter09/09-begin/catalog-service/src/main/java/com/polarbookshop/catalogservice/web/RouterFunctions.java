package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.config.PolarProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration
public class RouterFunctions {

	@Bean
	RouterFunction<ServerResponse> routes(PolarProperties properties) {
		return route(GET("/"), request ->
				ok().body(properties.greeting()));
	}

}
