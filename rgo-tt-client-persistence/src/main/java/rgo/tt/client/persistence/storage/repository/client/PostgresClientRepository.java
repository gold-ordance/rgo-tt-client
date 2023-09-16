package rgo.tt.client.persistence.storage.repository.client;

import rgo.tt.client.persistence.storage.entity.Client;
import rgo.tt.client.persistence.storage.sqlstatement.client.ClientSqlStatement;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlstatement.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;

import java.util.List;
import java.util.Optional;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.getFirstEntity;

public class PostgresClientRepository implements ClientRepository {

    private final StatementJdbcTemplateAdapter jdbc;

    public PostgresClientRepository(StatementJdbcTemplateAdapter jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Client> findAll() {
        SqlReadStatement<Client> statement = ClientSqlStatement.findAll();
        SqlReadResult<Client> result = jdbc.read(statement);
        return result.getEntities();
    }

    @Override
    public Optional<Client> findByEntityId(Long entityId) {
        SqlReadStatement<Client> statement = ClientSqlStatement.findByEntityId(entityId);
        SqlReadResult<Client> result = jdbc.read(statement);
        return getFirstEntity(result.getEntities());
    }

    @Override
    public Client save(Client client) {
        FetchEntityById<Client> function = this::getEntityById;
        SqlCreateStatement<Client> statement = ClientSqlStatement.save(client, function);
        SqlCreateResult<Client> result = jdbc.save(statement);
        return result.getEntity();
    }
}
