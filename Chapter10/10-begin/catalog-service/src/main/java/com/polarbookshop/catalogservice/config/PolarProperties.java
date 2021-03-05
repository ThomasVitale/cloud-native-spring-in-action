package com.polarbookshop.catalogservice.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
@Data
public class PolarProperties {

	/**
	 * A message to welcome users.
	 */
	private String greeting;
}
