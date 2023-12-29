package rgo.tt.user.rest.api.client;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.Response;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;
import rgo.tt.user.rest.api.client.response.ClientGetEntityResponse;
import rgo.tt.user.rest.api.client.response.ClientGetListResponse;
import rgo.tt.user.rest.api.client.response.ClientModifyResponse;
import rgo.tt.user.service.client.ClientService;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.armeria.rest.HttpDataProcessor.mapToHttp;
import static rgo.tt.user.rest.api.client.ClientMapper.map;

public class InternalRestClientService implements RestClientService {

    private final ClientService service;

    public InternalRestClientService(ClientService service) {
        this.service = service;
    }

    @Override
    public HttpResponse findAll() {
        List<Client> clients = service.findAll();
        Response response = ClientGetListResponse.success(map(clients));
        return mapToHttp(response);
    }

    @Override
    public HttpResponse findByEntityId(Long entityId) {
        Optional<Client> client = service.findByEntityId(entityId);
        Response response = client.isPresent()
                ? ClientGetEntityResponse.success(map(client.get()))
                : ErrorResponse.notFound();

        return mapToHttp(response);
    }

    @Override
    public HttpResponse save(ClientSaveRequest rq) {
        Client client = service.save(map(rq));
        Response response = ClientModifyResponse.saved(map(client));
        return mapToHttp(response);
    }
}
