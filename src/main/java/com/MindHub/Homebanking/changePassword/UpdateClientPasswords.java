package com.MindHub.Homebanking.changePassword;

import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateClientPasswords implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository; // Asegúrate de tener tu repositorio de clientes inyectado aquí.

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder ya configurado en tu aplicación.

    @Override
    public void run(String... args) throws Exception {
        // Obtén todos los clientes de la base de datos.
        List<Client> clients = clientRepository.findAll();

        // Genera y establece nuevas contraseñas seguras.
        for (Client client : clients) {
            // Genera una nueva contraseña segura, por ejemplo: "Password@123"
            String newPassword = "Banco123."; // Cambia esto por la lógica que necesites para generar las contraseñas.

            // Encripta la nueva contraseña.
            String encodedPassword = passwordEncoder.encode(newPassword);

            // Actualiza la contraseña del cliente.
            client.setPassword(encodedPassword);

            // Guarda los cambios en la base de datos.
            clientRepository.save(client);
        }

        System.out.println("Contraseñas de los clientes actualizadas con éxito.");
    }
}

