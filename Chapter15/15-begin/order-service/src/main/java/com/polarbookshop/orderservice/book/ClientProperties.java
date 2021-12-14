package com.polarbookshop.orderservice.book;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.net.URI;

@ConfigurationProperties(prefix = "polar")
public record ClientProperties (

	@NotNull
	URI catalogServiceUrl

){}
