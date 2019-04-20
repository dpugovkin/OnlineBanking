package com.pugovkin.onlinebanking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class Client extends AbstractEntity {

    private String name;
    private String address;
    private int age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Account> accounts;
}