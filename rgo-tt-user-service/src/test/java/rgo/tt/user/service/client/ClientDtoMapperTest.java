package rgo.tt.user.service.client;

import org.junit.jupiter.api.Test;
import rgo.tt.user.persistence.storage.entity.Client;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;

class ClientDtoMapperTest {

    @Test
    void map_toClientDto() {
        Client client = randomClient();
        ClientDto dto = ClientDtoMapper.map(client);
        compare(dto, client);
    }

    @Test
    void map_toClientDtoList() {
        List<Client> clients = List.of(randomClient());
        List<ClientDto> dtoList = ClientDtoMapper.map(clients);

        assertThat(clients).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), clients.get(0));
    }

    private void compare(ClientDto dto, Client client) {
        assertThat(dto.getEntityId()).isEqualTo(client.getEntityId());
        assertThat(dto.getEmail()).isEqualTo(client.getEmail());
        assertThat(dto.getCreatedDate()).isEqualTo(client.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(client.getLastModifiedDate());
    }
}
