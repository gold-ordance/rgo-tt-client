package rgo.tt.user.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.user.persistence.storage.entity.Client;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.validator.ValidatorUtils.validateObjectId;
import static rgo.tt.common.validator.ValidatorUtils.validateString;

public class ValidateClientServiceDecorator implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateClientServiceDecorator.class);

    private final ClientService delegate;

    public ValidateClientServiceDecorator(ClientService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Client> findAll() {
        LOGGER.info("Request 'findAll' received.");
        return delegate.findAll();
    }

    @Override
    public Optional<Client> findByEntityId(Long entityId) {
        LOGGER.info("Request 'findByEntityId' received: entityId={}", entityId);
        validateObjectId(entityId, "entityId");
        return delegate.findByEntityId(entityId);
    }

    @Override
    public Client save(Client client) {
        LOGGER.info("Request 'save' received: client={}", client);
        validateString(client.getEmail(), "email");
        validateString(client.getPassword(), "password");
        return delegate.save(client);
    }
}
