package com.pugovkin.onlinebanking.service;


import com.pugovkin.onlinebanking.entity.Account;
import com.pugovkin.onlinebanking.entity.Transaction;
import com.pugovkin.onlinebanking.repository.TransactionRepository;
import com.pugovkin.onlinebanking.utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        return transactionRepository.findAllByOrderByTimeStampDesc();
    }

    public Transaction getById(Long id) {
        return transactionRepository.getOne(id);
    }

    public boolean makeTransfer(Account source, Account destination, BigDecimal amount, LocalDateTime time) {
        if (time == null)
            time = LocalDateTime.now();
        //if source null - cash top-up

        if (source != null) {
            if (amount.compareTo(BigDecimal.ZERO) <= 0 || source.getBalance().compareTo(amount) < 0) {
                return false;
            }
            Transaction senderTransaction = new Transaction();
            senderTransaction.setTimeStamp(time);
            senderTransaction.setAccount(source);
            senderTransaction.setTransactionType(TransactionType.WITHDRAW);
            senderTransaction.setAmount(amount);
            accountService.changeBalance(source, senderTransaction, amount);
        }

        //if destination null - cash withdraw

        if (destination != null) {
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setTimeStamp(time);
            receiverTransaction.setAccount(destination);
            receiverTransaction.setTransactionType(TransactionType.TOP_UP);
            receiverTransaction.setAmount(amount);
            accountService.changeBalance(destination, receiverTransaction, amount);
        }
        return true;
    }

    public boolean newTransfer(MultiValueMap<String, String> formData) {
        try {
            Account from = processRadioButton(formData, "radioFrom", "fromAccount");
            Account to = processRadioButton(formData, "radioTo", "toAccount");
            BigDecimal amount = new BigDecimal(formData.getFirst("amount"));
            return makeTransfer(from, to, amount, null);
        } catch (Exception e) {
            return false;
        }
    }

    private Account processRadioButton(MultiValueMap<String, String> formData, String radio, String account) throws Exception {
        switch (formData.getFirst(radio)) {
            case "cash":
                return null;
            case "account":
                return accountService.getById(Long.parseLong(formData.getFirst(account)));

        }
        return null;
    }

    public List<Transaction> getClientTransactions(String dateFrom, String dateTo, Long clientId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime from;
        if (dateFrom.isEmpty()) {
            from = LocalDateTime.of(2000, 1, 1, 0, 0);
        } else
            from = LocalDate.parse(dateFrom, formatter).atStartOfDay();
        LocalDateTime to;
        if (dateTo.isEmpty()) {
            to = LocalDateTime.now();
        } else
            to = LocalDate.parse(dateTo, formatter).atTime(LocalTime.MAX);
        System.out.println(from);
        System.out.println(to);
        if (clientId.equals(0L)) {
            return transactionRepository.findAllByTimeStampBetween(from, to);
        } else
            return transactionRepository.findAllByAccount_ClientIdAndTimeStampBetween(clientId, from, to);
    }
}