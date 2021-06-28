package com.polarbookshop.edgeservice;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfiguration {

	private static final Duration TIMEOUT = Duration.ofSeconds(5);

	@Bean
	Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.custom()
						.slidingWindowSize(20)
						.permittedNumberOfCallsInHalfOpenState(5)
						.failureRateThreshold(50)
						.waitDurationInOpenState(Duration.ofSeconds(60))
						.build())
				.timeLimiterConfig(TimeLimiterConfig.custom()
						.timeoutDuration(TIMEOUT)
						.build())
				.build()
		);
	}

}
