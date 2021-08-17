package com.polarbookshop.orderservice.book;

import java.net.URI;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "polar")
@ConstructorBinding
public record BookClientProperties(

	@NotNull
	URI catalogServiceUrl

){}
