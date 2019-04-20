package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(path = "/all")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        System.out.println("test redirect2");
        return "clients";
    }

    @GetMapping(path = "/{id}")
    public String getOneClient(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.getOneClient(id));
        return "client";
    }
}