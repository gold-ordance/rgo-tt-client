package rgo.tt.user.grpc.client.properties;

public interface GrpcClientProperties {

    String endpoint();
    long timeoutMillis();
    long outboundMessageSizeInBytes();
}
