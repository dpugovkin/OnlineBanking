package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.service.AccountService;
import com.pugovkin.onlinebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("transactions", transactionService.getAll());
        return "transactions";
    }

    @GetMapping(path = "/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transaction", transactionService.getById(id));
        return "transaction";
    }

    @GetMapping(path = "/new")
    public String newTrasactionView(Model model) {
        List<Account> accounts = accountService.getAll();
        model.addAttribute("accounts", accounts);
        return "new_transaction";
    }

    @PostMapping(path = "/new")
    public String newTrasaction() {
        return null;
    }
}