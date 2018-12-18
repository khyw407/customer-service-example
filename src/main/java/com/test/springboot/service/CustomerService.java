package com.test.springboot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.springboot.domain.Account;
import com.test.springboot.domain.Customer;
import com.test.springboot.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CacheManager cacheManager;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CustomerRepository customerRepository;
	
	public Customer add(Customer customer) {
		return customerRepository.add(customer);
	}
	
	public Customer update(Customer customer) {
		return customerRepository.update(customer);
	}
	
	public Customer findById(Long id) {
		return customerRepository.findById(id);
	}
	
	public List<Customer> find(List<Long> ids){
		return customerRepository.find(ids);
	}
	
	public void delete(Long id) {
		customerRepository.delete(id);
	}
	
	@CachePut("accounts")
	@HystrixCommand(commandKey = "account-service.findByCustomer", fallbackMethod = "findCustomerAccountsFallback", 
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
				})
	public List<Account> findCustomerAccounts(Long id){
		Account[] accounts = restTemplate.getForObject("http://zuul.192.168.0.9.nip.io:32001/api/account/{customerId}", Account[].class, id);
		return Arrays.stream(accounts).collect(Collectors.toList());
	}
	
	public List<Account> findCustomerAccountsFallback(Long id){
		ValueWrapper valueWraaper = cacheManager.getCache("accounts").get(id);
		
		if(valueWraaper != null) {
			return (List<Account>)valueWraaper.get();
		}else {
			return new ArrayList<>();
		}
	}
}