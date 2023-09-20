package rgo.tt.user.rest.api.client.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.user.rest.api.client.dto.ClientDto;

import java.util.List;

public class ClientGetListResponse implements Response {

    private Status status;
    private List<ClientDto> clients;

    public static ClientGetListResponse success(List<ClientDto> clients) {
        ClientGetListResponse response = new ClientGetListResponse();
        response.status = Status.success();
        response.clients = clients;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ClientDto> getClients() {
        return clients;
    }

    public void setClients(List<ClientDto> clients) {
        this.clients = clients;
    }
}
