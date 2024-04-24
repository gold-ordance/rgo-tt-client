package rgo.tt.user.grpc.client;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientServiceGrpc;
import rgo.tt.user.grpc.api.ProtoGenerator;
import rgo.tt.user.service.client.ClientDto;
import rgo.tt.user.service.client.ClientService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static rgo.tt.user.EntityGenerator.randomClient;
import static rgo.tt.user.grpc.api.GrpcClientFactory.createLocalClient;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterGrpcClientServiceTest {

    private static final int PORT = 8081;
    private static final int NUMBER_OF_RETRY_REQUESTS = 10;

    private ClientServiceGrpc.ClientServiceBlockingStub blockingClient;

    @Autowired private ClientService service;

    @BeforeEach
    void setUp() {
        blockingClient = createLocalClient(PORT);
    }

    @Test
    void findByUsername() {
        ClientDto saved = insert();
        ClientGetByUsernameRequest request = ProtoGenerator.createGetByUsernameRequest(saved.getEmail());

        try {
            for (int i = 0; i < NUMBER_OF_RETRY_REQUESTS; i++) {
                blockingClient.findByUsername(request);
            }
            fail();
        } catch (StatusRuntimeException e) {
            assertThat(e.getStatus().getCode()).isEqualTo(Status.UNAVAILABLE.getCode());
        }
    }

    private ClientDto insert() {
        return service.save(randomClient());
    }
}
