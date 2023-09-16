package rgo.tt.user.persistence.storage.repository.client;

import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.common.persistence.CommonRepository;

import java.util.List;

public interface ClientRepository extends CommonRepository<Client> {

    List<Client> findAll();

    Client save(Client client);
}
