package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Models.TypeTransaction;
import com.mindhub.homebanking.Repositories.AccountRepository;
import com.mindhub.homebanking.Repositories.ClientRepository;
import com.mindhub.homebanking.Repositories.TransactionRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mindhub.homebanking.Models.TypeTransaction.CREDITO;
import static com.mindhub.homebanking.Models.TypeTransaction.DEBITO;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository Accountrepository, TransactionRepository TransactionRepository) {
		return (args) -> {
			Client cliente1 = new Client("Melba", "Morel", "meal@mindhub.com");
			Client cliente2 = new Client("Diego", "Suarez", "diegoCorreo@mindhub.com");

			LocalDateTime date1 = LocalDateTime.now();
			LocalDateTime date2 = LocalDateTime.now().plusDays(1);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String date1Tem = date1.format(formatter);
			String date2Tem = date2.format(formatter);

			LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);
			LocalDateTime date2f = LocalDateTime.parse(date2Tem, formatter);

			Account account1 = new Account("VIN001",5000.00,date1f);
			Account account2 = new Account("VIN002",7500.00,date2f);

			Account account3 = new Account("VIN003",9000.00,date1f);
			Account account4 = new Account("VIN004",12500.00,date2f);

			Transaction transaction1 = new Transaction(TypeTransaction.DEBITO,565.67,"Compra de cerveza",date1f);
			Transaction transaction12 = new Transaction(TypeTransaction.DEBITO,325.52,"Compra de Tequila",date2f);
			Transaction transaction13 = new Transaction(TypeTransaction.CREDITO,1025.00,"Deposito Salario",date1f);
			Transaction transaction14 = new Transaction(TypeTransaction.DEBITO,125.13,"Compra de repuestos carro",date1f);

			Transaction transaction2 = new Transaction(DEBITO,1325.52,"Compra de cerveza",date1f);
			Transaction transaction22 = new Transaction(CREDITO,610.01,"Transferencias Novia",date1f);
			Transaction transaction23 = new Transaction(CREDITO,822.99,"Transferencia Internacional",date2f);
			Transaction transaction24 = new Transaction(CREDITO,57.47,"Devolucion Intereses",date1f);

			cliente1.addAccount(account1);
			cliente1.addAccount(account2);
			cliente2.addAccount(account3);
			cliente2.addAccount(account4);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction12);
			account1.addTransaction(transaction13);
			account1.addTransaction(transaction14);

			account2.addTransaction(transaction2);
			account2.addTransaction(transaction22);
			account2.addTransaction(transaction23);
			account2.addTransaction(transaction24);
			// save a couple of customers
			repository.save(cliente1);
			repository.save(cliente2);

			Accountrepository.save(account1);
			Accountrepository.save(account2);
			Accountrepository.save(account3);
			Accountrepository.save(account4);

			TransactionRepository.save(transaction1);
			TransactionRepository.save(transaction12);
			TransactionRepository.save(transaction13);
			TransactionRepository.save(transaction14);

			TransactionRepository.save(transaction2);
			TransactionRepository.save(transaction22);
			TransactionRepository.save(transaction23);
			TransactionRepository.save(transaction24);

			account1.setBalance(account1.getBalance()-transaction1.getAmount());
			account1.setBalance(account1.getBalance()-transaction12.getAmount());
			account1.setBalance(account1.getBalance()+transaction13.getAmount());
			account1.setBalance(account1.getBalance()-transaction14.getAmount());

			Accountrepository.save(account1);

			account2.setBalance(account2.getBalance()-transaction2.getAmount());
			account2.setBalance(account2.getBalance()+transaction22.getAmount());
			account2.setBalance(account2.getBalance()+transaction23.getAmount());
			account2.setBalance(account2.getBalance()+transaction24.getAmount());

			Accountrepository.save(account2);
		};
	}

}