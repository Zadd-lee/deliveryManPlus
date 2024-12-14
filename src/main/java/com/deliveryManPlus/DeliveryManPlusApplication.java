package com.deliveryManPlus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DeliveryManPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryManPlusApplication.class, args);
	}

}
