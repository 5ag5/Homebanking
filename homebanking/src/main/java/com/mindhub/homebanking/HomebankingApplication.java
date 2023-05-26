package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Repositories.*;
import com.mindhub.homebanking.Utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mindhub.homebanking.Models.TypeTransaction.CREDITO;
import static com.mindhub.homebanking.Models.TypeTransaction.DEBITO;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEnconder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository Accountrepository,
									  TransactionRepository TransactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client admin = new Client("admin", "admin", "admin@emailAdmin.com", passwordEnconder.encode("admin1234"));

			Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("melba"));
			Client cliente2 = new Client("Diego", "Suarez", "diegoCorreo@mindhub.com", passwordEnconder.encode("3EDC4RFV"));
			Client cliente3 = new Client("Cristina", "Correa", "cristinacorreo14@mindhub.com", passwordEnconder.encode("5TGB6YHN"));

			LocalDateTime date1 = LocalDateTime.now();
			LocalDateTime date2 = LocalDateTime.now().plusDays(1);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String date1Tem = date1.format(formatter);
			String date2Tem = date2.format(formatter);

			LocalDateTime date1f = LocalDateTime.parse(date1Tem, formatter);
			LocalDateTime date2f = LocalDateTime.parse(date2Tem, formatter);

			Account account1 = new Account("VIN001", 5000.00, date1f.minusYears(4), TypeAccount.CHECKING);
			Account account2 = new Account("VIN002", 7500.00, date2f,TypeAccount.SAVING);
			Account account5 = new Account("VIN005", 0, date2f,TypeAccount.CHECKING);


			Account account3 = new Account("VIN003", 9000.00, date1f,TypeAccount.CHECKING);
			Account account4 = new Account("VIN004", 12500.00, date2f,TypeAccount.CHECKING);


			Transaction transaction1 = new Transaction(TypeTransaction.DEBITO, 565.67, "beer purchase", date1f.minusYears(4),5008.38);
			Transaction transaction12 = new Transaction(TypeTransaction.DEBITO, 325.52, "tequila purchase", date2f,5574.35);
			Transaction transaction13 = new Transaction(TypeTransaction.CREDITO, 1025.00, "Salary Deposit", date1f,5899.87);
			Transaction transaction14 = new Transaction(TypeTransaction.DEBITO, 125.13, "Car parts purchase", date1f,4874.87);

			Transaction transaction2 = new Transaction(DEBITO, 1325.52, "beer purchase", date1f,7664.95);
			Transaction transaction22 = new Transaction(CREDITO, 610.01, "Account 1234 transfer", date1f,8990.47);
			Transaction transaction23 = new Transaction(CREDITO, 822.99, "International deposit", date2f,8380.46);
			Transaction transaction24 = new Transaction(CREDITO, 57.47, "Interes earnead", date1f,7557.47);

			List<Integer> payments1 = List.of(12, 24, 36, 48);
			List<Integer> payments2 = List.of(6, 12, 24);
			List<Integer> payments3 = List.of(6, 12, 24, 36);


			Loan hipotecario = new Loan("House Loan", 500000, payments1, 8.5, LoanUtils.interestsPayments);
			Loan personal = new Loan("Persona Loan", 100000, payments2, 25.5, LoanUtils.interestsPayments);
			Loan automotriz = new Loan("Car Loan", 300000, payments3, 15, LoanUtils.interestsPayments);

			ClientLoan clientLoan1 = new ClientLoan(400000,"LON002" ,60, "House Loan", hipotecario.getInterest());
			ClientLoan clientLoan11 = new ClientLoan(50000, "LON003",12, "Persona Loan", personal.getInterest());

			ClientLoan clientLoan2 = new ClientLoan(100000, "LON004",24, "Persona Loan", personal.getInterest());
			ClientLoan clientLoan21 = new ClientLoan(200000,"LON005" ,36, "Car Loan", automotriz.getInterest());

			LocalDate date1LC = LocalDate.now();

			Card card1 = new Card("Melba", "Morel", CardType.DEBIT, CardColor.GOLD,
					"4521-7895-5641-2585", 874, date1LC.minusYears(10).minusMonths(2), date1LC);

			Card card2 = new Card("Melba", "Morel", CardType.CREDIT, CardColor.TITANIUM,
					"7894-5613-1147-9512", 554, date1LC.plusYears(5), date1LC);

			Card card3 = new Card("Diego", "Suarez", CardType.CREDIT, CardColor.SILVER,
					"8525-9856-2237-1239", 635, date1LC.plusYears(5), date1LC);

			cliente1.addLoan(clientLoan1);
			cliente1.addLoan(clientLoan11);

			cliente2.addLoan(clientLoan2);
			cliente2.addLoan(clientLoan21);

			hipotecario.addClientLoan(clientLoan1);
			personal.addClientLoan(clientLoan11);
			personal.addClientLoan(clientLoan2);
			automotriz.addClientLoan(clientLoan21);

			cliente1.addAccount(account1);
			cliente1.addAccount(account2);
			cliente1.addAccount(account5);

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

			cliente1.addCard(card1);
			cliente1.addCard(card2);
			cliente2.addCard(card3);

			// save a couple of customers
			repository.save(cliente1);
			repository.save(cliente2);
			repository.save(cliente3);
			repository.save(admin);

			Accountrepository.save(account1);
			Accountrepository.save(account2);
			Accountrepository.save(account3);
			Accountrepository.save(account4);
			Accountrepository.save(account5);

			TransactionRepository.save(transaction1);
			TransactionRepository.save(transaction12);
			TransactionRepository.save(transaction13);
			TransactionRepository.save(transaction14);

			TransactionRepository.save(transaction2);
			TransactionRepository.save(transaction22);
			TransactionRepository.save(transaction23);
			TransactionRepository.save(transaction24);

			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan11);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan21);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

			 account1.setBalance(account1.getBalance() - transaction1.getAmount());
			 account1.setBalance(account1.getBalance() - transaction12.getAmount());
			 account1.setBalance(account1.getBalance() + transaction13.getAmount());
			 account1.setBalance(account1.getBalance() - transaction14.getAmount());

			Accountrepository.save(account1);

		};
	}
}

