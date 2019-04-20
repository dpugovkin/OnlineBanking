package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(path = "/all")
    public String getAll(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "clients";
    }

    @GetMapping(path = "/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("client", clientService.getById(id));
        return "client";
    }

    @PostMapping(path = "/new")
    public String addNew(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("age") int age) {
        clientService.add(name, address, age);
        System.out.println(name + " " + address + " " + age);
        return "redirect:/client/all";
    }
}