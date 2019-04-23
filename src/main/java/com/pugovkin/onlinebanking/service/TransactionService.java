package com.pugovkin.onlinebanking.service;


import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.entity.Transaction;
import com.pugovkin.onlinebanking.repository.TransactionRepository;
import com.pugovkin.onlinebanking.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        return transactionRepository.findAllByOrderByTimeStampDesc();
    }

    public Transaction getById(Long id) {
        return transactionRepository.getOne(id);
    }

    public boolean makeTransfer(Account source, Account destination, BigDecimal amount) {

        //if source null - cash top-up

        if (source != null) {
            if (source.getBalance().compareTo(amount) == -1) {
                return false;
            }
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
        return true;
    }

    public boolean newTransfer(MultiValueMap<String, String> formData) {
        Account from = processRadioButton(formData, "radioFrom", "fromAccount");
        Account to = processRadioButton(formData, "radioTo", "toAccount");
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(formData.getFirst("amount"))));
        return makeTransfer(from, to, amount);
    }

    private Account processRadioButton(MultiValueMap<String, String> formData, String radio, String account) {
        switch (Objects.requireNonNull(formData.getFirst(radio))) {
            case "cash":
                return null;
            case "account":
                return accountService.getById(Long.parseLong(Objects.requireNonNull(formData.getFirst(account))));

        }
        return null;
    }
}