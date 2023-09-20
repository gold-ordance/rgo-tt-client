package rgo.tt.user.rest.api.client;

import com.linecorp.armeria.common.HttpResponse;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

public interface RestClientService {

    HttpResponse findAll();

    HttpResponse findByEntityId(Long entityId);

    HttpResponse save(ClientSaveRequest rq);
}
