package com.MindHub.Homebanking.controllers;

import com.MindHub.Homebanking.dtos.ClientDTO;
import com.MindHub.Homebanking.models.Client;
import com.MindHub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClientDTO(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        if (clientService.getClientById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clientService.getClientDTO(clientService.getClientById(id)), HttpStatus.OK);
        }

    }
}
//    //crud crear cliente
//    @PostMapping("/create")  //tipo post para crear un cliente
//    public Client createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
//        return clientRepository.save(new Client(firstName, lastName, email, password));
//    }
//
//    //crud para actualizar un cliente
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
//        Client client = clientRepository.findById(id).orElse(null);
//        if (client == null) {
//            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
//        }
//        client.setFirstName(firstName);
//        client.setLastName(lastName);
//        client.setEmail(email);
//
//        Client updatedClient = clientRepository.save(client);
//        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
//    }
//
//    @PatchMapping("/update/{id}")
//    public ResponseEntity<?> partialUpdateClient(
//            @PathVariable Long id,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String email) {
//
//        Client client = clientRepository.findById(id).orElse(null);
//
//        if (client == null) {
//            return new ResponseEntity<>("Client not found with id " + id, HttpStatus.NOT_FOUND);
//        }
//
//        if (firstName != null) {
//            client.setFirstName(firstName);
//        }
//        if (lastName != null) {
//            client.setLastName(lastName);
//        }
//        if (email != null) {
//            client.setEmail(email);
//        }
//
//        Client updatedClient = clientRepository.save(client);
//        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
//    }
//
//
//    //crud para borrar un cliente
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity <String> deleteClient(@PathVariable Long id) {
//
//        clientRepository.deleteById(id);
//
//        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
//    }


