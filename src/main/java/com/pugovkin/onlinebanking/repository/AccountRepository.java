package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends AbstractRepository<Account> {
    List<Account> findAllByOrderByBalanceDesc();
}
