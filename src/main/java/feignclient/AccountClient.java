package feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import domain.Account;

@FeignClient(name = "account-service")
public interface AccountClient {
	
	@GetMapping("/customer/{customerId}")
	public List<Account> findByCustomer(@PathVariable("customerId") Long customerId);
}
