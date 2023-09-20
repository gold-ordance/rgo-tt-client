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
        compare(client, dto);
    }

    private void compare(Client client, ClientDto dto) {
        assertThat(client.getEntityId()).isEqualTo(dto.getEntityId());
        assertThat(client.getEmail()).isEqualTo(dto.getEmail());
        assertThat(client.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(client.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
    }

    @Test
    void map_toClientDtoList() {
        List<Client> clients = List.of(randomClient());
        List<ClientDto> dtoList = ClientMapper.map(clients);

        assertThat(clients).hasSize(1);
        assertThat(dtoList).hasSize(1);
        compare(clients.get(0), dtoList.get(0));
    }

    @Test
    void map_toClient() {
        ClientSaveRequest rq = createClientSaveRequest();
        Client client = ClientMapper.map(rq);

        assertThat(rq.getEmail()).isEqualTo(client.getEmail());
        assertThat(rq.getPassword()).isEqualTo(client.getPassword());

        List<String> nonEmptyFields = List.of("email", "password");
        validateNullFieldsExcept(client, nonEmptyFields);
    }
}
