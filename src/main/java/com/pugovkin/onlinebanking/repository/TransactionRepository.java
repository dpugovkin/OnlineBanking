package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends AbstractRepository<Transaction> {

    List<Transaction> findAllByOrderByTimeStampDesc();

    List<Transaction> findAllByAccount_ClientIdAndTimeStampBetween(Long id, LocalDateTime start, LocalDateTime end);

    List<Transaction> findAllByTimeStampBetween(LocalDateTime from, LocalDateTime to);
}
