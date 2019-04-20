package com.pugovkin.onlinebanking.service;


import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.entity.Transaction;
import com.pugovkin.onlinebanking.repository.TransactionRepository;
import com.pugovkin.onlinebanking.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.getOne(id);
    }

    public void makeTransfer(Account source, Account destination, BigDecimal amount) {

        //if source null - cash top-up

        if (source != null) {
            Transaction senderTransaction = new Transaction();
            senderTransaction.setTimeStamp(LocalDateTime.now());
            senderTransaction.setAccount(source);
            senderTransaction.setTransactionType(TransactionType.WITHDRAW);
            senderTransaction.setAmount(amount);
            accountService.changeBalance(source, senderTransaction, amount);
        }

        //if destination null - cash withdraw

        if (destination != null) {
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setTimeStamp(LocalDateTime.now());
            receiverTransaction.setAccount(destination);
            receiverTransaction.setTransactionType(TransactionType.TOP_UP);
            receiverTransaction.setAmount(amount);
            accountService.changeBalance(destination, receiverTransaction, amount);
        }
    }

}