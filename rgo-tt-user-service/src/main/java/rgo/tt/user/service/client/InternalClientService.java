package rgo.tt.user.service.client;

import rgo.tt.user.persistence.storage.repository.client.ClientRepository;

import java.util.List;
import java.util.Optional;

import static rgo.tt.user.service.client.ClientDtoMapper.map;

public class InternalClientService implements ClientService {

    private final ClientRepository repository;

    public InternalClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ClientDto> findAll() {
        return map(repository.findAll());
    }

    @Override
    public Optional<ClientDto> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId)
                .map(ClientDtoMapper::map);
    }

    @Override
    public Optional<ClientDto> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(ClientDtoMapper::map);
    }

    @Override
    public ClientDto save(ClientDto client) {
        return map(repository.save(map(client)));
    }
}
