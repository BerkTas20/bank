package com.bank.bank;

import com.bank.bank.model.Account;
import com.bank.bank.model.City;
import com.bank.bank.model.Customer;
import com.bank.bank.repository.AccountRepository;
import com.bank.bank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BankApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public BankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Customer> customerList = Arrays.asList();
		Customer c1 = Customer.builder()
				.id("123456")
				.name("Example")
				.city(City.ISTANBUL)
				.dateOfBirth(1990)
				.build();
		Customer c2 = Customer.builder()
				.id("1234")
				.name("John")
				.city(City.DENIZLI)
				.dateOfBirth(1999)
				.build();
		Customer c3 = Customer.builder()
				.id("123")
				.name("Michael")
				.city(City.IZMIR)
				.dateOfBirth(1998)
				.build();
		customerRepository.saveAll(Arrays.asList(c1,c2,c3));

		Account a1 = Account.builder()
				.id("1")
				.customerId("123")
				.city(City.ISTANBUL)
				.balance(133350.0)
				.build();
		Account a2= Account.builder()
				.id("2")
				.customerId("123123")
				.city(City.KOCAELI)
				.balance(25100.0)
				.build();
		Account a3 = Account.builder()
				.id("3")
				.customerId("123789")
				.city(City.MARAS)
				.balance(10000.0)
				.build();
		accountRepository.saveAll(Arrays.asList(a1,a2,a3));
	}
}
//CommandLineRunner = Uygulama ayağa kalkarken veritabanına data eklemek istersek kullanıyoruz

