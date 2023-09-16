package rgo.tt.user.service.client;

import rgo.tt.user.persistence.storage.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    Optional<Client> findByEntityId(Long entityId);

    Client save(Client client);
}
