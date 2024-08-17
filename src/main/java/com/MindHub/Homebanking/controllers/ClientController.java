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
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired   //cablear a la interfaz clientRepository,para poder usar los metodos de jpa repo. cableado automatico
    private ClientRepository clientRepository; //se instancia client repository, interfaz, lo va a implementar hibernate. inyeccion de dependencias

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/hello")    //Esta anotación mapea la ruta "/hola" a la getClients() método. Significa que cuando recibimos una solicitud GET en la ruta "/hola" (la ruta completa sería http://localhost:8080/api/clients/hello), este método será invocado.
    public String getClients() { //mapping, asociado
        return "Hello clients!";
    }

    @GetMapping("/")   //para mapear la ruta /api/clients/ a un método que devuelva todos los clientes de la base de datos.por defecto uso la ruta de arriba
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()//lista de todos los clientes en la base de datos
                .stream() //para acceder a los metodos d orden sup
                .map(client -> new ClientDTO(client))
                .collect(toList());
    }

    //mappear la ruta a una solicitud de tipo GET
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
    public Client createClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {  //request param le dice a spring que solicito ese parametro
        return clientRepository.save(new Client(firstName, lastName, email)); //validar q no sean string vacios, if first name.isBlank() return false
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

        Client updatedClient = clientRepository.save(client);  //sobreescribo el cliente que ya tenia
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
