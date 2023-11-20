package rgo.tt.user.grpc.service.client;

import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.persistence.storage.entity.Client;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientGetEntityResponse map(Client client) {
        return ClientGetEntityResponse.newBuilder()
                .setUsername(client.getEmail())
                .setPassword(client.getPassword())
                .build();
    }
}
