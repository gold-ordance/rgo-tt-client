package rgo.tt.user.persistence.storage.repository.client;

import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.common.persistence.DbTxManager;

import java.util.List;
import java.util.Optional;

public class TxClientRepositoryDecorator implements ClientRepository {

    private final ClientRepository delegate;
    private final DbTxManager tx;

    public TxClientRepositoryDecorator(ClientRepository delegate, DbTxManager tx) {
        this.delegate = delegate;
        this.tx = tx;
    }

    @Override
    public List<Client> findAll() {
        return tx.tx(delegate::findAll);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return tx.tx(() -> delegate.findByEmail(email));
    }

    @Override
    public Optional<Client> findByEntityId(Long entityId) {
        return tx.tx(() -> delegate.findByEntityId(entityId));
    }

    @Override
    public Client save(Client client) {
        return tx.tx(() -> delegate.save(client));
    }
}
