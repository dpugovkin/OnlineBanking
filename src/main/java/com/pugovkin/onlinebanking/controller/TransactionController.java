package com.pugovkin.onlinebanking.controller;

import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.service.AccountService;
import com.pugovkin.onlinebanking.service.ClientService;
import com.pugovkin.onlinebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ClientService clientService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService, ClientService clientService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("clients", clientService.getAll());
        model.addAttribute("transactions", transactionService.getAll());
        return "transactions";
    }

    @PostMapping(path = "/filter")
    public String getFiltered(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, @RequestParam("client") Long clientId, Model model) {
        model.addAttribute("clients", clientService.getAll());
        model.addAttribute("transactions", transactionService.getClientTransactions(dateFrom, dateTo, clientId));
        return "transactions";
    }

    @GetMapping(path = "/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transaction", transactionService.getById(id));
        return "transaction";
    }

    @GetMapping(path = "/new")
    public String newTransactionView(Model model) {
        List<Account> accounts = accountService.getAll();
        model.addAttribute("accounts", accounts);
        return "new_transaction";
    }

    @PostMapping(path = "/new")
    public String newTransaction(@RequestBody MultiValueMap<String, String> formData, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("result", transactionService.newTransfer(formData));
        redirectAttributes.addFlashAttribute("message", "Transaction completed");
        return "redirect:/transactions/new";
    }
}