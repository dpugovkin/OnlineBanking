package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("accounts", accountService.getAll());
        return "accounts";
    }

    @GetMapping(path = "/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("account", accountService.getById(id));
        return "account";
    }

    @PostMapping()
    public String addNew(@RequestParam("clientid") Long clientId, Model model) {
        accountService.add(clientId);
        return "redirect:/clients/" + clientId;
    }
}