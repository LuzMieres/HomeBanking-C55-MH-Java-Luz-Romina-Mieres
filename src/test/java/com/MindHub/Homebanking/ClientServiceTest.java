//package com.MindHub.Homebanking;
//
//import com.MindHub.Homebanking.dtos.ClientDTO;
//import com.MindHub.Homebanking.models.Client;
//import com.MindHub.Homebanking.repositories.ClientRepository;
//import com.MindHub.Homebanking.services.ClientService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class ClientServiceTest {
//
//    @Autowired
//    private ClientService clientService;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    // Test para obtener todos los clientes
//    @Test
//    @Transactional
//    public void testGetAllClient() {
//        List<Client> clients = clientService.getAllClient();
//        assertNotNull(clients);  // Verifica que la lista no sea nula
//        assertTrue(clients.size() > 0);  // Verifica que haya al menos un cliente en la base de datos
//    }
//
//    // Test para obtener todos los clientes como DTO
//    @Test
//    @Transactional
//    public void testGetAllClientDTO() {
//        List<ClientDTO> clientDTOs = clientService.getAllClientDTO();
//        assertNotNull(clientDTOs);  // Verifica que la lista no sea nula
//        assertTrue(clientDTOs.size() > 0);  // Verifica que haya al menos un cliente convertido a DTO
//    }
//
//    // Test para obtener un cliente por ID
//    @Test
//    @Transactional
//    public void testGetClientById() {
//        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("123"));
//        clientRepository.save(client);  // Guardar un cliente en la base de datos para la prueba
//
//        Client foundClient = clientService.getClientById(client.getId());
//        assertNotNull(foundClient);  // Verifica que el cliente se haya encontrado
//        assertEquals(client.getId(), foundClient.getId());  // Verifica que el cliente encontrado sea el correcto
//    }
//
//    // Test para obtener un cliente como DTO
//    @Test
//    @Transactional
//    public void testGetClientDTO() {
//        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("123"));
//        clientRepository.save(client);  // Guardar un cliente en la base de datos para la prueba
//
//        ClientDTO clientDTO = clientService.getClientDTO(client);
//        assertNotNull(clientDTO);  // Verifica que el cliente DTO no sea nulo
//        assertEquals(client.getId(), clientDTO.getId());  // Verifica que los IDs coincidan
//    }
//
//    // Test para encontrar un cliente por email
//    @Test
//    @Transactional
//    public void testFindByEmail() {
//        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("123"));
//        clientRepository.save(client);  // Guardar un cliente en la base de datos para la prueba
//
//        Client foundClient = clientService.findByEmail("john.doe@example.com");
//        assertNotNull(foundClient);  // Verifica que el cliente se haya encontrado
//        assertEquals(client.getEmail(), foundClient.getEmail());  // Verifica que el email coincida
//    }
//}
//
