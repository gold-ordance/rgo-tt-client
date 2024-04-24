package rgo.tt.user.rest.api.client;

import rgo.tt.user.service.client.ClientDto;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

public final class RequestMapper {

    private RequestMapper() {
    }

    public static ClientDto map(ClientSaveRequest rq) {
        ClientDto dto = new ClientDto();
        dto.setEmail(rq.getEmail());
        dto.setPassword(rq.getPassword());
        return dto;
    }
}
