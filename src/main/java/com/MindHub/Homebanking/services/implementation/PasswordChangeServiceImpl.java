package com.MindHub.Homebanking.services.implementation;

import com.MindHub.Homebanking.dtos.PasswordChangeDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.ClientRepository;
import com.MindHub.Homebanking.services.PasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(String email, PasswordChangeDTO passwordChangeDTO) throws Exception {
        // Busca el cliente por su email
        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new Exception("Usuario no encontrado."));

        // Verifica si la contraseña actual es correcta
        if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), client.getPassword())) {
            throw new Exception("La contraseña actual no es correcta.");
        }

        // Verifica si la nueva contraseña cumple con las políticas de seguridad
        if (!isValidPassword(passwordChangeDTO.getNewPassword())) {
            throw new Exception("La nueva contraseña no cumple con las políticas de seguridad.");
        }

        // Encripta la nueva contraseña y la actualiza en la base de datos
        String newEncodedPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
        client.setPassword(newEncodedPassword);

        // Guarda el cliente actualizado
        clientRepository.save(client);
    }

    // Método para verificar si la nueva contraseña cumple con las políticas
    private boolean isValidPassword(String password) {
        // Aquí puedes implementar las validaciones que necesites,
        // por ejemplo, longitud mínima, letras mayúsculas, minúsculas, números, caracteres especiales, etc.
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}
