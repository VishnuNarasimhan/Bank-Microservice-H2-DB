package com.bank.bankloanservice;

import com.bank.bankloanservice.dto.LoanContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = LoanContactInfoDto.class)
public class BankLoanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankLoanServiceApplication.class, args);
		System.out.println("APPLICATION STARTED");
	}
}
