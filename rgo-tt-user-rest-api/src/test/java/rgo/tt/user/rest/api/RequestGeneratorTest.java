package rgo.tt.user.rest.api;

import org.junit.jupiter.api.Test;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import static rgo.tt.common.utils.TestUtils.validateNonNullFields;

class RequestGeneratorTest {

    @Test
    void createClientSaveRequest() {
        ClientSaveRequest rq = RequestGenerator.createClientSaveRequest();
        validateNonNullFields(rq);
    }
}
