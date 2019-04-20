package com.pugovkin.onlinebanking.service;

import com.pugovkin.onlinebanking.entity.Client;
import com.pugovkin.onlinebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getOneClient(int id) {
        return clientRepository.getOne(id);
    }
}
