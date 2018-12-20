package com.test.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.domain.Account;
import com.test.springboot.domain.Customer;
import com.test.springboot.service.CustomerService;

@RestController
public class CustomerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	CustomerService customerService;
	
	@PostMapping
	public Customer add(@RequestBody Customer customer) {
		return customerService.add(customer);
	}
	
	@PutMapping
	public Customer update(@RequestBody Customer customer) {
		return customerService.update(customer);
	}
	
	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") Long id) {
		return customerService.findById(id);
	}
	
	@GetMapping("/withAccounts/{id}")
	public Customer findByIdWithAccounts(@PathVariable("id") Long id) throws Exception{
		Customer customer = customerService.findById(id);
		List<Account> accounts = customerService.findCustomerAccounts(id);
		
		LOGGER.info("Accounts found: {}", mapper.writeValueAsString(accounts));
		
		customer.setAccounts(accounts);
		return customer;
	}
	
	@PostMapping("/ids")
	public List<Customer> find(@RequestBody List<Long> ids){
		return customerService.find(ids);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		customerService.delete(id);
	}
}
