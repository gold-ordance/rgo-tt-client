package rgo.tt.user.grpc.service.client;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.grpc.ClientServiceGrpc;
import rgo.tt.user.grpc.api.ProtoGenerator;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.user.service.ServiceConfig;
import rgo.tt.user.service.client.ClientService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.grpc.test.simpleserver.GrpcServerManager.getPort;
import static rgo.tt.common.grpc.test.simpleserver.GrpcServerManager.startGrpcServer;
import static rgo.tt.common.grpc.test.simpleserver.GrpcServerManager.stopServer;
import static rgo.tt.user.grpc.api.GrpcClientFactory.createLocalClient;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
class GrpcClientServiceTest {

    private ClientServiceGrpc.ClientServiceBlockingStub blockingClient;

    @Autowired private ClientService service;

    @BeforeEach
    void setUp() throws IOException {
        H2PersistenceUtils.truncateTables();
        startGrpcServer(new GrpcClientService(service));
        blockingClient = createLocalClient(getPort());
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findByUsername_notFound() {
        ClientGetByUsernameRequest request = ProtoGenerator.randomGetByUsernameRequest();
        assertThatThrownBy(() -> blockingClient.findByUsername(request))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage("NOT_FOUND: Client not found for username: " + request.getUsername());
    }

    @Test
    void findByUsername_found() {
        Client saved = insert();
        ClientGetByUsernameRequest request = ProtoGenerator.createGetByUsernameRequest(saved.getEmail());

        ClientGetEntityResponse response = blockingClient.findByUsername(request);

        assertThat(response.getUsername()).isEqualTo(saved.getEmail());
        assertThat(response.getPassword()).isEqualTo(saved.getPassword());
    }

    private Client insert() {
        return service.save(randomClient());
    }
}
