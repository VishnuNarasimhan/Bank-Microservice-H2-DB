package com.bank.BankGatewayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class BankGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankGatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator BankGatewayRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
			.route(p -> p
				.path("/banks/accounts/**")
				.filters( f -> f.rewritePath("/banks/accounts/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
								.setFallbackUri("forward:/contactSupport")))
				.uri("lb://ACCOUNTS"))
			.route(p -> p
				.path("/banks/loans/**")
				.filters( f -> f.rewritePath("/banks/loans/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://LOANS"))
			.route(p -> p
				.path("/banks/cards/**")
				.filters( f -> f.rewritePath("/banks/cards/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://CARDS"))
			.build();
	}

}
