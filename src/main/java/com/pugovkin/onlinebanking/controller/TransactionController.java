package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("transactions", transactionService.getAll());
        return "transactions";
    }

    @GetMapping(path = "{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transaction", transactionService.getById(id));
        return "transaction";
    }
}