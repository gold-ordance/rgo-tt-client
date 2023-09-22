package rgo.tt.user.rest.api.client.response;

import rgo.tt.common.rest.api.Response;
import rgo.tt.common.rest.api.Status;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.user.rest.api.client.dto.ClientDto;

public class ClientModifyResponse implements Response {

    private Status status;
    private ClientDto client;

    public static ClientModifyResponse saved(ClientDto client) {
        return response(StatusCode.STORED, client);
    }

    private static ClientModifyResponse response(StatusCode code, ClientDto client) {
        ClientModifyResponse response = new ClientModifyResponse();
        response.status = Status.success(code);
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
}
