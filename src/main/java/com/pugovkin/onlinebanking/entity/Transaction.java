package com.pugovkin.onlinebanking.entity;

import com.pugovkin.onlinebanking.utils.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated
    private TransactionType transactionType;

    private LocalDateTime timeStamp;
    private BigDecimal amount;
}