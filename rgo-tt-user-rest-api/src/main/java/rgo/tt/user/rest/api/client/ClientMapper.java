package rgo.tt.user.rest.api.client;

import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.rest.api.client.dto.ClientDto;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import java.util.List;

public final class ClientMapper {

    private ClientMapper() {
    }

    public static ClientDto map(Client client) {
        ClientDto dto = new ClientDto();
        dto.setEntityId(client.getEntityId());
        dto.setEmail(client.getEmail());
        dto.setCreatedDate(client.getCreatedDate());
        dto.setLastModifiedDate(client.getLastModifiedDate());
        return dto;
    }

    public static List<ClientDto> map(List<Client> clients) {
        return clients.stream()
                .map(ClientMapper::map)
                .toList();
    }

    public static Client map(ClientSaveRequest rq) {
        return Client.builder()
                .setEmail(rq.getEmail())
                .setPassword(rq.getPassword())
                .build();
    }
}
