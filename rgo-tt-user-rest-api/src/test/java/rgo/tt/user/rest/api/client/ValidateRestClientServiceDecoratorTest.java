package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.user.rest.api.RestConfig;
import rgo.tt.user.rest.api.client.dto.ClientDto;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;
import rgo.tt.user.rest.api.client.response.ClientGetEntityResponse;
import rgo.tt.user.rest.api.client.response.ClientModifyResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.get;
import static rgo.tt.common.rest.api.utils.test.ArmeriaClientManager.post;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.startServerWithService;
import static rgo.tt.common.rest.api.utils.test.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class ValidateRestClientServiceDecoratorTest {

    @Autowired private RestClientService restService;

    @BeforeEach
    void setUp() {
        startServerWithService(restService);
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNegative() {
        long negativeEntityId = -randomPositiveLong();

        String json = get("/" + negativeEntityId);
        ClientGetEntityResponse response = fromJson(json, ClientGetEntityResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is negative.");
        assertThat(actual).isNull();
    }

    @Test
    void save_invalidRq_emailIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail("");

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The email is empty.");
        assertThat(actual).isNull();
    }

    @Test
    void save_invalidRq_passwordIsNull() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword(null);

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is null.");
        assertThat(actual).isNull();
    }

    @Test
    void save_invalidRq_passwordIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword("");

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is empty.");
        assertThat(actual).isNull();
    }
}
