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

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getById(long id) {
        return clientRepository.getOne(id);
    }

    public void add(String name, String address, int age) {
        Client client = new Client();
        client.setName(name);
        client.setAge(age);
        client.setAddress(address);
        clientRepository.save(client);
    }
}
