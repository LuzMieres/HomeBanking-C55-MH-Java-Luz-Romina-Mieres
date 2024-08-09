package com.MindHub.Homebanking;

import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.models.Transaction;
import com.MindHub.Homebanking.models.TransactionType;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return args -> {
			//Creo y guardo al client Melba
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(melba);

			//Creo, le añado sus cuentas a Melba y las guardo
			Account account1 = new Account("VIN001",5000.0, LocalDate.now());
			Account account2 = new Account("VIN002", 7500.0, LocalDate.now().plusDays(1));
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

			//Creo y guardo al client Ana
			Client ana = new Client("Ana", "Gonzalez", "anaGonzalez@gmail.com");
			clientRepository.save(ana);

			//Creo, le añado sus cuentas a Ana y las guardo
			Account account3 = new Account("VIN003", 2000.0, LocalDate.now());
			Account account4 = new Account("VIN004", 3000.0, LocalDate.now().plusDays(2));
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

			//Creo y guardo al client Luz
			Client luz = new Client("Luz", "Mieres", "luzmieres@gmail.com");
			clientRepository.save(luz);

			//Creo, le añado a Luz sus cuentas y las guardo
			Account account5 = new Account("VIN005", 4000.0, LocalDate.now());
			Account account6 = new Account("VIN006", 5000.0, LocalDate.now().plusDays(3));
			luz.addAccount(account5);
			luz.addAccount(account6);
			accountRepository.save(account5);
			accountRepository.save(account6);

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

			//Souts de los 3 Clientes
			System.out.println(melba);
			System.out.println(ana);
			System.out.println(luz);

		};
	}
}
