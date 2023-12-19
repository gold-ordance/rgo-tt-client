package rgo.tt.user.grpc.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rgo.tt.user.grpc.ClientServiceGrpc;

public final class GrpcClientFactory {

    private GrpcClientFactory() {
    }

    public static ClientServiceGrpc.ClientServiceBlockingStub createLocalClient(int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
        return ClientServiceGrpc.newBlockingStub(channel);
    }
}
