package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.Test;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.rest.api.client.dto.ClientDto;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.TestUtils.validateNullFieldsExcept;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;

class ClientMapperTest {

    @Test
    void map_toClientDto() {
        Client client = randomClient();
        ClientDto dto = ClientMapper.map(client);
        compare(dto, client);
    }

    @Test
    void map_toClientDtoList() {
        List<Client> clients = List.of(randomClient());
        List<ClientDto> dtoList = ClientMapper.map(clients);

        assertThat(clients).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(dtoList.get(0), clients.get(0));
    }

    @Test
    void map_toClient() {
        ClientSaveRequest rq = createClientSaveRequest();
        Client client = ClientMapper.map(rq);

        assertThat(client.getEmail()).isEqualTo(rq.getEmail());
        assertThat(client.getPassword()).isEqualTo(rq.getPassword());

        List<String> nonEmptyFields = List.of("email", "password");
        validateNullFieldsExcept(client, nonEmptyFields);
    }

    private void compare( ClientDto dto, Client client) {
        assertThat(dto.getEntityId()).isEqualTo(client.getEntityId());
        assertThat(dto.getEmail()).isEqualTo(client.getEmail());
        assertThat(dto.getCreatedDate()).isEqualTo(client.getCreatedDate());
        assertThat(dto.getLastModifiedDate()).isEqualTo(client.getLastModifiedDate());
    }
}
