package com.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import domain.Customer;
import domain.CustomerType;
import repository.CustomerRepository;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Bean
	CustomerRepository customerRepository() {
		CustomerRepository customerRepository = new CustomerRepository();
		customerRepository.add(new Customer("John Scott", CustomerType.NEW));
		customerRepository.add(new Customer("Adam Smith", CustomerType.REGULAR));
		customerRepository.add(new Customer("Jacob Ryan", CustomerType.VIP));
		
		return customerRepository;
	}
}
