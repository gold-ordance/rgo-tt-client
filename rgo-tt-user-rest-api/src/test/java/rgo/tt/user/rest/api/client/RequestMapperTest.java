package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.Test;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;
import rgo.tt.user.service.client.ClientDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.TestUtils.validateNullFieldsExcept;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;

class RequestMapperTest {

    @Test
    void map_toClient() {
        ClientSaveRequest rq = createClientSaveRequest();
        ClientDto client = RequestMapper.map(rq);

        assertThat(client.getEmail()).isEqualTo(rq.getEmail());
        assertThat(client.getPassword()).isEqualTo(rq.getPassword());

        List<String> nonEmptyFields = List.of("email", "password");
        validateNullFieldsExcept(client, nonEmptyFields);
    }
}
