package rgo.tt.user.service.client;

import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.persistence.storage.repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

public class InternalClientService implements ClientService {

    private final ClientRepository repository;


    public InternalClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Client save(Client client) {
        return repository.save(client);
    }
}
