package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);
		System.out.println("Holas");
	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository Accountrepository) {
		Client cliente1 = new Client("Melba", "Morel", "meal@mindhub.com");
		Client cliente2 = new Client("Diego", "Suarez", "diegoCorreo@mindhub.com");

		LocalDateTime date1 = LocalDateTime.now();
		LocalDateTime date2 = LocalDateTime.now().plusDays(1);

		Account account1 = new Account("123abc",5000.00,date1);
		Account account2 = new Account("456lft",7500.00,date2);

		Account account3 = new Account("789okm",9000.00,date1);
		Account account4 = new Account("963ssw",12500.00,date2);

		cliente1.addAccount(account1);
		cliente1.addAccount(account2);
		cliente2.addAccount(account3);
		cliente2.addAccount(account4);


		return (args) -> {
			// save a couple of customers
			repository.save(cliente1);
			repository.save(cliente2);

			Accountrepository.save(account1);
			Accountrepository.save(account2);
			Accountrepository.save(account3);
			Accountrepository.save(account4);

		};
	}

}