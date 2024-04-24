package rgo.tt.user.service.client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientDto> findAll();

    Optional<ClientDto> findByEntityId(Long entityId);

    Optional<ClientDto> findByEmail(String email);

    ClientDto save(ClientDto client);
}
