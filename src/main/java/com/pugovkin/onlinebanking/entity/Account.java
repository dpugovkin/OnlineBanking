package com.pugovkin.onlinebanking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    private BigDecimal balance;

}