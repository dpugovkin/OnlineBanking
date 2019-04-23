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
import java.time.LocalDateTime;
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
        addTrasnaction(null, clients.get(0), BigDecimal.valueOf(1000), LocalDateTime.of(2019, 4, 5, 13, 0));
        addTrasnaction(null, clients.get(0), BigDecimal.valueOf(110), LocalDateTime.of(2019, 4, 7, 17, 0));
        addTrasnaction(clients.get(0), null, BigDecimal.valueOf(300), LocalDateTime.of(2019, 3, 5, 9, 45));
        addTrasnaction(null, clients.get(1), BigDecimal.valueOf(500), LocalDateTime.of(2019, 4, 10, 19, 0));
        addTrasnaction(null, clients.get(2), BigDecimal.valueOf(700), LocalDateTime.of(2019, 4, 15, 12, 10));
    }


    private void saveClient(String name, String address, int age) {
        Client client = new Client();
        client.setName(name);
        client.setAddress(address);
        client.setAge(age);
        List<Account> accounts = new ArrayList<>();
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
    void addTrasnaction(Client from, Client to, BigDecimal amount, LocalDateTime time) {
        if (from == null)
            transactionService.makeTransfer(null, to.getAccounts().get(0), amount, time);
        else if (to == null)
            transactionService.makeTransfer(from.getAccounts().get(0), null, amount, time);
        else
            transactionService.makeTransfer(from.getAccounts().get(0), to.getAccounts().get(0), amount, time);
    }
}
