package rgo.tt.user.service.client;

import rgo.tt.user.persistence.storage.entity.Client;

import java.util.List;

final class ClientDtoMapper {

    private ClientDtoMapper() {
    }

    public static Client map(ClientDto dto) {
        return Client.builder()
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword())
                .build();
    }

    public static ClientDto map(Client client) {
        ClientDto dto = new ClientDto();
        dto.setEntityId(client.getEntityId());
        dto.setEmail(client.getEmail());
        dto.setPassword(client.getPassword());
        dto.setCreatedDate(client.getCreatedDate());
        dto.setLastModifiedDate(client.getLastModifiedDate());
        return dto;
    }

    public static List<ClientDto> map(List<Client> clients) {
        return clients.stream()
                .map(ClientDtoMapper::map)
                .toList();
    }
}
