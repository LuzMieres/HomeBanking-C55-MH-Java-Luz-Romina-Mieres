package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.repositories.AccountRepository;
import com.MindHub.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/hello")
    public String getClients() { //mapping, asociado
        return "Hello clients!";
    }

    @GetMapping("/")
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtainClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id).orElse(null);

        if (client == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found, sorry, try again later!");
        }
        ClientDTO clientDTO = new ClientDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    //crud crear cliente
    @PostMapping("/create")  //tipo post para crear un cliente
    public Client createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        return clientRepository.save(new Client(firstName, lastName, email, password));
    }

    //crud para actualizar un cliente
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
        }
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);

        Client updatedClient = clientRepository.save(client);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> partialUpdateClient(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {

        Client client = clientRepository.findById(id).orElse(null);

        if (client == null) {
            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
        }

        if (firstName != null) {
            client.setFirstName(firstName);
        }
        if (lastName != null) {
            client.setLastName(lastName);
        }
        if (email != null) {
            client.setEmail(email);
        }

        Client updatedClient = clientRepository.save(client);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }


    //crud para borrar un cliente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <String> deleteClient(@PathVariable Long id) {

        clientRepository.deleteById(id);

        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }

}
