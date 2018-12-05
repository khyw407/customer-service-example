package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import domain.Account;
import domain.Customer;
import feignclient.AccountClient;
import repository.CustomerRepository;

@RestController
public class CustomerController {

	@Autowired
	AccountClient accountClient;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping
	public Customer add(@RequestBody Customer customer) {
		return customerRepository.add(customer);
	}
	
	@PutMapping
	public Customer update(@RequestBody Customer customer) {
		return customerRepository.update(customer);
	}
	
	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") Long id) {
		return customerRepository.findById(id);
	}
	
	@GetMapping("/withAccounts/{id}")
	public Customer findByIdWithAccounts(@PathVariable("id") Long id) throws Exception{
		List<Account> accounts = accountClient.findByCustomer(id);
		
		Customer customer = customerRepository.findById(id);
		customer.setAccounts(accounts);
		return customer;
	}
	
	@PostMapping("/ids")
	public List<Customer> find(@RequestBody List<Long> ids){
		return customerRepository.find(ids);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		customerRepository.delete(id);
	}
}
