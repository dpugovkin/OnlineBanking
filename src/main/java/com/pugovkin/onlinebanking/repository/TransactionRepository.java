package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends AbstractRepository<Transaction> {
}
