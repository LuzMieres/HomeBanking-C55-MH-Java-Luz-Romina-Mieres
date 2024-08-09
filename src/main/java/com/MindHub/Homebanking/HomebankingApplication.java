package com.MindHub.Homebanking;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData (ClientRepository clientRepository){
		return args -> {
			//Creo y guardo al client Melba
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(melba);

			//Creo y guardo al client Ana
			Client ana = new Client("Ana", "Gonzalez", "anaGonzalez@gmail.com");
			clientRepository.save(ana);

			//Creo y guardo al client Luz
			Client luz = new Client("Luz", "Mieres", "luzmieres@gmail.com");
			clientRepository.save(luz);

			//Souts de los 3 Clientes
			System.out.println(melba);
			System.out.println(ana);
			System.out.println(luz);

		};
	}
}
