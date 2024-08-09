package com.MindHub.Homebanking;

import com.MindHub.Homebanking.models.Account;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository, AccountRepository accountRepository) {
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

			//Souts de los 3 Clientes
			System.out.println(melba);
			System.out.println(ana);
			System.out.println(luz);

		};
	}
}
