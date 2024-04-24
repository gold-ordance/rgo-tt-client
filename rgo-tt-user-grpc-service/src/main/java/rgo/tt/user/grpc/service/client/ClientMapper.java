package rgo.tt.user.grpc.service.client;

import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.service.client.ClientDto;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientGetEntityResponse map(ClientDto client) {
        return ClientGetEntityResponse.newBuilder()
                .setUsername(client.getEmail())
                .setPassword(client.getPassword())
                .build();
    }
}
