package com.pugovkin.onlinebanking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends AbstractEntity {

    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Account targetAccount;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Account sourceAccount;

    private boolean withdraw;
    private BigDecimal amount;
}