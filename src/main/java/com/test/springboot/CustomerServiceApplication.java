package com.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.test.springboot.domain.Customer;
import com.test.springboot.domain.CustomerType;
import com.test.springboot.repository.CustomerRepository;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableCaching
public class CustomerServiceApplication {

	@LoadBalanced
	@Bean
	RestTemplate restTeamplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("accounts");
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