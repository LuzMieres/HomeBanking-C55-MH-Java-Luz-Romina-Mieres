//package com.MindHub.Homebanking;
//
//import com.MindHub.Homebanking.dtos.LoginDTO;
//import com.MindHub.Homebanking.dtos.RegisterDTO;
//import com.MindHub.Homebanking.dtos.ClientDTO;
//import com.MindHub.Homebanking.models.Client;
//import com.MindHub.Homebanking.repositories.ClientRepository;
//import com.MindHub.Homebanking.services.AuthService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class AuthServiceTest {
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    // Test para el método login
//    @Test
//    @Transactional
//    public void testLogin() {
//        // Registrar un nuevo cliente para probar el login
//        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john.doe@example.com", "password123");
//        authService.register(registerDTO);
//
//        // Intentar hacer login con las credenciales correctas
//        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "password123");
//        String token = authService.login(loginDTO);
//
//        assertNotNull(token);  // Verifica que se genere un token JWT
//    }
//
//    // Test para el método login con credenciales incorrectas
//    @Test
//    @Transactional
//    public void testLoginInvalidCredentials() {
//        // Registrar un nuevo cliente
//        RegisterDTO registerDTO = new RegisterDTO("Jane", "Doe", "jane.doe@example.com", "password123");
//        authService.register(registerDTO);
//
//        // Intentar hacer login con credenciales incorrectas
//        LoginDTO loginDTO = new LoginDTO("jane.doe@example.com", "wrongpassword");
//
//        Exception exception = assertThrows(BadCredentialsException.class, () -> {
//            authService.login(loginDTO);
//        });
//
//        assertEquals("Bad credentials", exception.getMessage());
//    }
//
//    // Test para el método register
//    @Test
//    @Transactional
//    public void testRegister() {
//        // Crear un nuevo cliente
//        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john.doe@example.com", "password123");
//        Client newClient = authService.register(registerDTO);
//
//        assertNotNull(newClient);  // Verifica que el cliente se haya registrado correctamente
//        assertEquals("John", newClient.getFirstName());  // Verifica el nombre del cliente
//        assertEquals("Doe", newClient.getLastName());  // Verifica el apellido del cliente
//
//        // Verificar que el cliente se haya guardado en la base de datos
//        Client clientFromDB = clientRepository.findByEmail("john.doe@example.com").orElse(null);
//        assertNotNull(clientFromDB);  // Verifica que el cliente existe en la base de datos
//    }
//
//    // Test para el método register con un email ya existente
//    @Test
//    @Transactional
//    public void testRegisterDuplicateEmail() {
//        // Registrar un cliente
//        RegisterDTO registerDTO = new RegisterDTO("Jane", "Doe", "jane.doe@example.com", "password123");
//        authService.register(registerDTO);
//
//        // Intentar registrar otro cliente con el mismo email
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            RegisterDTO duplicateDTO = new RegisterDTO("Jane", "Doe", "jane.doe@example.com", "password123");
//            authService.register(duplicateDTO);
//        });
//
//        assertEquals("Email already in use", exception.getMessage());
//    }
//
//    // Test para obtener el cliente actual por email
//    @Test
//    @Transactional
//    public void testGetCurrentClient() {
//        // Registrar un cliente
//        RegisterDTO registerDTO = new RegisterDTO("John", "Doe", "john.doe@example.com", "password123");
//        authService.register(registerDTO);
//
//        // Obtener el cliente actual usando su email
//        ClientDTO clientDTO = authService.getCurrentClient("john.doe@example.com");
//
//        assertNotNull(clientDTO);  // Verifica que se haya devuelto un cliente
//        assertEquals("John", clientDTO.getFirstName());  // Verifica que el nombre sea correcto
//        assertEquals("Doe", clientDTO.getLastName());  // Verifica que el apellido sea correcto
//    }
//
//    // Test para obtener un cliente que no existe
//    @Test
//    @Transactional
//    public void testGetCurrentClientNotFound() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            authService.getCurrentClient("nonexistent@example.com");
//        });
//
//        assertEquals("Client with email nonexistent@example.com not found", exception.getMessage());
//    }
//}
//
//
