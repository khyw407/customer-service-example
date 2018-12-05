package domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Customer {

	private Long id;
	private String name;
	private CustomerType type;
	private List<Account> accounts = new ArrayList<>();

	public Customer(String name, CustomerType type) {
		this.name = name;
		this.type = type;
	}
}
