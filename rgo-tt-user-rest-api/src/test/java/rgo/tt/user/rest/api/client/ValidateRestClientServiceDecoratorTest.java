package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.user.rest.api.RestConfig;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.get;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.post;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startArmeriaServer;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class ValidateRestClientServiceDecoratorTest {

    @Autowired private RestClientService restService;

    @BeforeEach
    void setUp() {
        startArmeriaServer(restService);
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findByEntityId_invalidRq_entityIdIsNegative() {
        long negativeEntityId = -randomPositiveLong();

        String json = get("/" + negativeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The entityId is negative.");
    }

    @Test
    void save_invalidRq_emailIsNull() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The email is null.");
    }

    @Test
    void save_invalidRq_emailIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail("");

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The email is empty.");
    }

    @Test
    void save_invalidRq_passwordIsNull() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword(null);

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is null.");
    }

    @Test
    void save_invalidRq_passwordIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword("");

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is empty.");
    }
}
