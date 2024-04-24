package rgo.tt.user.rest.api.client.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.user.service.client.ClientDto;

public class ClientGetEntityResponse implements Response {

    private Status status;
    private ClientDto client;

    public static ClientGetEntityResponse success(ClientDto client) {
        ClientGetEntityResponse response = new ClientGetEntityResponse();
        response.status = Status.success();
        response.client = client;
        return response;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ClientGetEntityResponse{" +
                "status=" + status +
                ", client=" + client +
                '}';
    }
}
