package com.pugovkin.onlinebanking.service;

import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.entity.Client;
import com.pugovkin.onlinebanking.entity.Transaction;
import com.pugovkin.onlinebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final ClientService clientService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(ClientService clientService, AccountRepository accountRepository) {
        this.clientService = clientService;
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAllByOrderByBalanceDesc();
    }

    public Account getById(long id) {
        return accountRepository.getOne(id);
    }

    public void changeBalance(Account account, Transaction transaction, BigDecimal amount) {
        account.getTransactions().add(transaction);
        switch (transaction.getTransactionType()) {
            case WITHDRAW:
                account.setBalance(account.getBalance().subtract(amount));
                accountRepository.save(account);
                break;
            case TOP_UP:
                account.setBalance(account.getBalance().add(amount));
                accountRepository.save(account);
                break;
        }
    }

    @Transactional
    public void add(Long clientId) {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(0));
        Client client = clientService.getById(clientId);
        account.setClient(client);
        client.getAccounts().add(account);
    }
}