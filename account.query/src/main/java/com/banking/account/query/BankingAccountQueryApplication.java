package com.banking.account.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankingAccountQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAccountQueryApplication.class, args);
	}

}
