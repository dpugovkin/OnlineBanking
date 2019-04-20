package com.pugovkin.onlinebanking;

import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.entity.Client;
import com.pugovkin.onlinebanking.repository.ClientRepository;
import com.pugovkin.onlinebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DBInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    @Autowired
    public DBInitializer(ClientRepository clientRepository, TransactionService transactionService) {
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) throws Exception {
        saveClient("Dima", "Odessa", 27);
        saveClient("Andrey", "Odessa", 26);
        saveClient("Igor", "Cherkasy", 23);
        saveClient("Oleg", "Kiev", 32);
        List<Client> clients = clientRepository.findAll();
        addTrasnaction(clients.get(0), clients.get(1), BigDecimal.valueOf(100));
    }


    private void saveClient(String name, String address, int age) {
        Client client = new Client();
        client.setName(name);
        client.setAddress(address);
        client.setAge(age);
        List<Account> accounts = new ArrayList<>();
        accounts.add(createAccount(client, 0));
        accounts.add(createAccount(client, 0));
        client.setAccounts(accounts);
        clientRepository.save(client);
    }

    private Account createAccount(Client client, int balance) {
        Account account = new Account();
        account.setClient(client);
        account.setBalance(new BigDecimal(balance));
        return account;
    }

    @Transactional
    void addTrasnaction(Client from, Client to, BigDecimal amount) {
        transactionService.makeTransfer(from.getAccounts().get(0), to.getAccounts().get(0), amount);
    }
}
