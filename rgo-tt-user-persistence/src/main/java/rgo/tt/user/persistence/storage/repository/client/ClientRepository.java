package rgo.tt.user.persistence.storage.repository.client;

import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.common.persistence.CommonRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CommonRepository<Client> {

    List<Client> findAll();

    Optional<Client> findByEmail(String email);

    Client save(Client client);
}
