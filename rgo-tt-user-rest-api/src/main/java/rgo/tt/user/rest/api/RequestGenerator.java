package rgo.tt.user.rest.api;

import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import static rgo.tt.common.utils.RandomUtils.randomString;

public final class RequestGenerator {

    private RequestGenerator() {
    }

    public static ClientSaveRequest createClientSaveRequest() {
        ClientSaveRequest rq = new ClientSaveRequest();
        rq.setEmail(randomString());
        rq.setPassword(randomString());
        return rq;
    }
}
