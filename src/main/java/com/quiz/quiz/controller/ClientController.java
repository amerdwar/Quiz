package com.quiz.quiz.controller;



import com.quiz.quiz.dto.ListResponse;
import com.quiz.quiz.dto.ObjectResponse;
import com.quiz.quiz.entity.Client;
//import jdk.internal.ref.Cleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quiz.quiz.service.ClientService;

import java.util.Map;


@RestController
@RequestMapping("Client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public ResponseEntity<?> createClient(@RequestBody Client client){

        return ResponseEntity.ok(clientService.createClient(client));
    }


    @GetMapping("/getAllClients")
    public ResponseEntity<?> getAllClients(@RequestParam(defaultValue = "-1") int pageSize
            , @RequestParam(defaultValue = "0")  int pageNumber ){
        //System.out.println(itemMaxNumber+"");
        return ResponseEntity .ok(clientService.getAllClients(pageSize,pageNumber));
    }

    @RequestMapping(value = "/updateClient/{id}", method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateGeneric(
            @RequestBody Map<String, Object> updates,
            @PathVariable("id") int id) {

        ObjectResponse<Client> client = clientService.updateClient(updates,id);
        return ResponseEntity.ok(client);
    }
}
