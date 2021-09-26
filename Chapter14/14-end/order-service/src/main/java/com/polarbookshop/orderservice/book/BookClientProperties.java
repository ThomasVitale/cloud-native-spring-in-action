package com.polarbookshop.orderservice.book;

import java.net.URI;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public record BookClientProperties(

		@NotNull
		URI catalogServiceUrl

){}
