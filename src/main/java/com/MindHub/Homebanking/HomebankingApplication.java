package com.MindHub.Homebanking;

import com.MindHub.Homebanking.models.*;
import com.MindHub.Homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return args -> {
			//Client Melba
			Client melba = new Client("Melba", "Morel", "melbamorel@gmailcom");
			clientRepository.save(melba);

			//Account Client Melba
			Account account1 = new Account("VIN001", LocalDate.now(), 5000.0);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0);
			melba.addAccount(account1);
			melba.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);

			//Transactions Client Melba
			Transaction pagoServicioLuzMelba = new Transaction(TransactionType.DEBIT, -3000, "DEBIN PAGO SERVICIO", LocalDateTime.now());
			Transaction transferenciaRecibida1Melba = new Transaction(TransactionType.CREDIT, 1500, "CR INTERBANK", LocalDateTime.now().plusDays(1));
			Transaction pagoInternetMelba = new Transaction(TransactionType.DEBIT, -1000, "DEBIN PAGO SERVICIO", LocalDateTime.now().plusDays(1));
			Transaction transferenciaRecibida2Melba = new Transaction(TransactionType.CREDIT, 1000, "CR INTERBANK", LocalDateTime.now().plusDays(2));
			account1.addTransaction(pagoServicioLuzMelba);
			account1.addTransaction(transferenciaRecibida1Melba);
			account2.addTransaction(transferenciaRecibida2Melba);
			account2.addTransaction(pagoInternetMelba);
			transactionRepository.save(pagoServicioLuzMelba);
			transactionRepository.save(transferenciaRecibida1Melba);
			transactionRepository.save(transferenciaRecibida2Melba);
			transactionRepository.save(pagoInternetMelba);

			//Loans
			Loan hipotecario = new Loan("Hipotecario", 500000, Arrays.asList(12, 24, 36, 48, 60, 72));
			Loan personal = new Loan("Personal", 100000, Arrays.asList(6, 12, 24));
			Loan automotriz = new Loan("Automotriz", 300000, Arrays.asList(6, 12, 24, 36));
			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);

			//Loans Client Melba
			ClientLoan hipotecarioLoanMelba = new ClientLoan(400000, 48);
			melba.addClientLoan(hipotecarioLoanMelba);
			hipotecario.addClientLoan(hipotecarioLoanMelba);
			clientLoanRepository.save(hipotecarioLoanMelba);

			ClientLoan personalLoanMelba = new ClientLoan(100000, 12);
			melba.addClientLoan(personalLoanMelba);
			personal.addClientLoan(personalLoanMelba);
			clientLoanRepository.save(personalLoanMelba);

			ClientLoan automotrizLoanMelba = new ClientLoan(250000, 36);
			melba.addClientLoan(automotrizLoanMelba);
			automotriz.addClientLoan(automotrizLoanMelba);
			clientLoanRepository.save(automotrizLoanMelba);

			//Cards
			//DEBIT CARDS
			Card debitCardGold = new Card(CardType.DEBIT, ColorType.GOLD, LocalDate.now(), LocalDate.now().plusYears(5));
			debitCardGold.setNumber(debitCardGold.generarNumeroTarjeta());
			debitCardGold.setCvv(debitCardGold.generarCVV());


			Card debitCardSilver = new Card(CardType.DEBIT, ColorType.SILVER, LocalDate.now(), LocalDate.now().plusYears(5));
			debitCardSilver.setNumber(debitCardSilver.generarNumeroTarjeta());
			debitCardSilver.setCvv(debitCardSilver.generarCVV());


			Card debitCardTitanium = new Card(CardType.DEBIT, ColorType.TITANIUM, LocalDate.now(), LocalDate.now().plusYears(5));
			debitCardTitanium.setNumber(debitCardTitanium.generarNumeroTarjeta());
			debitCardTitanium.setCvv(debitCardGold.generarCVV());


			//CREDIT CARDS
			Card creditCardGold = new Card(CardType.CREDIT, ColorType.GOLD, LocalDate.now(), LocalDate.now().plusYears(5));
			creditCardGold.setNumber(creditCardGold.generarNumeroTarjeta());
			creditCardGold.setCvv(creditCardGold.generarCVV());


			Card creditCardSilver = new Card(CardType.CREDIT, ColorType.SILVER, LocalDate.now(), LocalDate.now().plusYears(5));
			creditCardSilver.setNumber(creditCardSilver.generarNumeroTarjeta());
			creditCardSilver.setCvv(creditCardGold.generarCVV());


			Card creditCardTitanium = new Card(CardType.CREDIT, ColorType.TITANIUM, LocalDate.now(), LocalDate.now().plusYears(5));
			creditCardTitanium.setNumber(creditCardTitanium.generarNumeroTarjeta());
			creditCardTitanium.setCvv(creditCardTitanium.generarCVV());

			//Añado las tarjetas de Melba
			melba.addCard(debitCardGold);
			cardRepository.save(debitCardGold);
			melba.addCard(creditCardTitanium);
			cardRepository.save(creditCardTitanium);


			//Client Ana
			Client ana = new Client("Ana", "Gonzalez", "anagonzalez@gmail.com");
			clientRepository.save(ana);

			//Accounts Client Ana
			Account account3 = new Account("VIN003", LocalDate.now(), 12000.0);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(2), 13000.0);
			ana.addAccount(account3);
			ana.addAccount(account4);
			accountRepository.save(account3);
			accountRepository.save(account4);

			//Transactions Client Ana
			Transaction pagoServicioAguaAna = new Transaction(TransactionType.DEBIT, -2000, "DEBIN PAGO SERVICIO", LocalDateTime.now());
			Transaction transferenciaRecibida1Ana = new Transaction(TransactionType.CREDIT, 1000, "CR INTERBANK", LocalDateTime.now().plusDays(2));
			Transaction pagoInternetAna = new Transaction(TransactionType.DEBIT, -1000, "DEBIN PAGO SERVICIO", LocalDateTime.now().plusDays(1));
			Transaction transferenciaRecibida2Ana = new Transaction(TransactionType.CREDIT, 1000, "CR INTERBANK", LocalDateTime.now().plusDays(2));
			account3.addTransaction(pagoServicioAguaAna);
			account3.addTransaction(transferenciaRecibida1Ana);
			account4.addTransaction(transferenciaRecibida2Ana);
			account4.addTransaction(pagoInternetAna);
			transactionRepository.save(pagoServicioAguaAna);
			transactionRepository.save(transferenciaRecibida1Ana);
			transactionRepository.save(transferenciaRecibida2Ana);
			transactionRepository.save(pagoInternetAna);


			//Loans Client Ana
			ClientLoan hipotecarioLoanAna = new ClientLoan(300000, 36);
			ana.addClientLoan(hipotecarioLoanAna);
			hipotecario.addClientLoan(hipotecarioLoanAna);
			clientLoanRepository.save(hipotecarioLoanAna);

			ClientLoan personalLoanAna = new ClientLoan(80000, 12);
			ana.addClientLoan(personalLoanAna);
			personal.addClientLoan(personalLoanAna);
			clientLoanRepository.save(personalLoanAna);

			ClientLoan automotrizLoanAna = new ClientLoan(200000, 24);
			ana.addClientLoan(automotrizLoanAna);
			automotriz.addClientLoan(automotrizLoanAna);
			clientLoanRepository.save(automotrizLoanAna);

			//Añado las cards de Ana
			ana.addCard(debitCardSilver);
			cardRepository.save(debitCardSilver);
			ana.addCard(creditCardGold);
			cardRepository.save(creditCardGold);


			//Client Luz
			Client luz = new Client("Luz", "Mieres", "luzmieres@gmail.com");
			clientRepository.save(luz);

			//Accounts ClientLuz
			Account account5 = new Account("VIN005", LocalDate.now(), 14000.0);
			Account account6 = new Account("VIN006", LocalDate.now().plusDays(3), 15000.0);
			luz.addAccount(account5);
			luz.addAccount(account6);
			accountRepository.save(account5);
			accountRepository.save(account6);

			//Transactions Client Luz
			Transaction transferenciaEnviadaLuz = new Transaction(TransactionType.DEBIT, -3000, "DEBIN PAGO SERVICIO", LocalDateTime.now());
			Transaction transferenciaRecibida1Luz = new Transaction(TransactionType.CREDIT, 2000, "CR INTERBANK", LocalDateTime.now().plusDays(3));
			Transaction pagoInternetLuz = new Transaction(TransactionType.DEBIT, -1000, "DEBIN PAGO SERVICIO", LocalDateTime.now().plusDays(1));
			Transaction transferenciaRecibida2Luz = new Transaction(TransactionType.CREDIT, 1000, "CR INTERBANK", LocalDateTime.now().plusDays(2));
			account5.addTransaction(transferenciaEnviadaLuz);
			account5.addTransaction(transferenciaRecibida1Luz);
			account6.addTransaction(transferenciaRecibida2Luz);
			account6.addTransaction(pagoInternetLuz);
			transactionRepository.save(transferenciaEnviadaLuz);
			transactionRepository.save(transferenciaRecibida1Luz);
			transactionRepository.save(transferenciaRecibida2Luz);
			transactionRepository.save(pagoInternetLuz);

			//Loans Client Luz
			ClientLoan hipotecarioLoanLuz = new ClientLoan(500000, 60);
			luz.addClientLoan(hipotecarioLoanLuz);
			hipotecario.addClientLoan(hipotecarioLoanLuz);
			clientLoanRepository.save(hipotecarioLoanLuz);

			ClientLoan personalLoanLuz = new ClientLoan(100000, 24);
			luz.addClientLoan(personalLoanLuz);
			personal.addClientLoan(personalLoanLuz);
			clientLoanRepository.save(personalLoanLuz);

			ClientLoan automotrizLoanLuz = new ClientLoan(300000, 36);
			luz.addClientLoan(automotrizLoanLuz);
			automotriz.addClientLoan(automotrizLoanLuz);
			clientLoanRepository.save(automotrizLoanLuz);

			//Añado las cards de Luz
			luz.addCard(debitCardTitanium);
			cardRepository.save(debitCardTitanium);
			luz.addCard(creditCardSilver);
			cardRepository.save(creditCardSilver);


			//Souts de los 3 Clients
			System.out.println(melba);
			System.out.println(ana);
			System.out.println(luz);
		};
	}
}
