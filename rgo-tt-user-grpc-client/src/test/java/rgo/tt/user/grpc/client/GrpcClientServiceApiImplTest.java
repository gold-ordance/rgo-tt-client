package rgo.tt.user.grpc.client;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.grpc.api.ProtoGenerator;
import rgo.tt.user.grpc.client.properties.GrpcClientProperties;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rgo.tt.common.grpc.test.simpleserver.GrpcServerManager.getPort;
import static rgo.tt.common.grpc.test.simpleserver.GrpcServerManager.startGrpcServer;

class GrpcClientServiceApiImplTest {

    private GrpcClientServiceApi client;
    private GrpcClientProperties config;

    @BeforeEach
    void setUp() throws IOException {
        startGrpcServer(new GrpcClientTestServer());

        config = mock(GrpcClientProperties.class);
        when(config.endpoint()).thenReturn("http://localhost:" + getPort());
    }

    @Test
    void findByUsername_success() {
        client = new GrpcClientServiceApiImpl(config);
        ClientGetByUsernameRequest request = ProtoGenerator.randomGetByUsernameRequest();

        ClientGetEntityResponse response = client.findByUsername(request);

        assertThat(response.getUsername()).isEqualTo(request.getUsername());
    }

    @Test
    void findByUsername_timeout() {
        long smallTimeoutMillis = 1L;
        when(config.timeoutMillis()).thenReturn(smallTimeoutMillis);
        client = new GrpcClientServiceApiImpl(config);
        ClientGetByUsernameRequest request = ProtoGenerator.randomGetByUsernameRequest();

        try {
            client.findByUsername(request);
            fail("Expected StatusRuntimeException with code DEADLINE_EXCEEDED.");
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage())
                    .contains(Status.DEADLINE_EXCEEDED.getCode().name());
        }
    }

    @Test
    void findByUsername_outboundMessageLimitExceeded() {
        long smallSizeInBytes = 1L;
        when(config.outboundMessageSizeInBytes()).thenReturn(smallSizeInBytes);
        client = new GrpcClientServiceApiImpl(config);
        ClientGetByUsernameRequest request = ProtoGenerator.randomGetByUsernameRequest();

        try {
            client.findByUsername(request);
            fail("Expected StatusRuntimeException with code RESOURCE_EXHAUSTED.");
        } catch (StatusRuntimeException e) {
            assertThat(e.getMessage())
                    .contains(Status.RESOURCE_EXHAUSTED.getCode().name());
        }
    }
}
