package com.bank.BankEurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BankEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankEurekaServerApplication.class, args);
		System.out.println("APPLICATION UP AND RUNNING");
	}

}
