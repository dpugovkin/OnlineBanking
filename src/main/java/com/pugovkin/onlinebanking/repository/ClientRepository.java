package com.pugovkin.onlinebanking.repository;

import com.pugovkin.onlinebanking.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends AbstractRepository<Client> {
    List<Client> findAllByOrderByNameAsc();
}
