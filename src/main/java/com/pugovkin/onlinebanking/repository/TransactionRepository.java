package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends AbstractRepository<Transaction> {

    List<Transaction> findAllByOrderByTimeStampDesc();
}
