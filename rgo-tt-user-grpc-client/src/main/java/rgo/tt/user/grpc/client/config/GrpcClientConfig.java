package rgo.tt.user.grpc.client.config;

public interface GrpcClientConfig {

    String endpoint();
    long timeoutMillis();
    long outboundMessageSizeInBytes();
}
