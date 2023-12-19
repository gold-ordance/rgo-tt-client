package rgo.tt.user.grpc.client;

import com.linecorp.armeria.client.Clients;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.grpc.ClientServiceGrpc;
import rgo.tt.user.grpc.client.properties.GrpcClientProperties;

public class GrpcClientServiceApiImpl implements GrpcClientServiceApi {

    private final ClientServiceGrpc.ClientServiceBlockingStub service;

    public GrpcClientServiceApiImpl(GrpcClientProperties properties) {
        this.service = Clients.builder("gproto+" + properties.endpoint())
                .responseTimeoutMillis(properties.timeoutMillis())
                .maxResponseLength(properties.outboundMessageSizeInBytes())
                .build(ClientServiceGrpc.ClientServiceBlockingStub.class);
    }

    @Override
    public ClientGetEntityResponse findByUsername(ClientGetByUsernameRequest rq) {
        return service.findByUsername(rq);
    }
}
