package rgo.tt.user.grpc.api;

import rgo.tt.user.grpc.ClientGetByUsernameRequest;

import java.util.UUID;

public final class ProtoGenerator {

    private ProtoGenerator() {
    }

    public static ClientGetByUsernameRequest createGetByUsernameRequest(String username) {
        return ClientGetByUsernameRequest.newBuilder()
                .setUsername(username)
                .build();
    }

    public static ClientGetByUsernameRequest randomGetByUsernameRequest() {
        return ClientGetByUsernameRequest.newBuilder()
                .setUsername(randomString())
                .build();
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
