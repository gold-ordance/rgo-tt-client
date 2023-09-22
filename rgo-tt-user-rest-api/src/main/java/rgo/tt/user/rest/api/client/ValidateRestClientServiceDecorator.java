package rgo.tt.user.rest.api.client;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.user.rest.api.ExceptionCommonHandler;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

@Blocking
@ExceptionHandler(ExceptionCommonHandler.class)
public class ValidateRestClientServiceDecorator implements RestClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateRestClientServiceDecorator.class);

    private final RestClientService delegate;

    public ValidateRestClientServiceDecorator(RestClientService delegate) {
        this.delegate = delegate;
    }

    @Get
    @Override
    public HttpResponse findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }

    @Get("/{entityId}")
    @Override
    public HttpResponse findByEntityId(@Param Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Post
    @Override
    public HttpResponse save(ClientSaveRequest rq) {
        LOGGER.info("Request 'save' received: request={}", rq);
        validateString(rq.getEmail(), "email");
        validateString(rq.getPassword(), "password");
        return delegate.save(rq);
    }
}
