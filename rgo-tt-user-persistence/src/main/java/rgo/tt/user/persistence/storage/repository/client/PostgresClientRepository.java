package rgo.tt.user.persistence.storage.repository.client;

import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlstatement.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.persistence.storage.sqlstatement.client.ClientSqlStatement;

import java.util.List;
import java.util.Optional;

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
        return result.getEntity();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        SqlReadStatement<Client> statement = ClientSqlStatement.findByEmail(email);
        SqlReadResult<Client> result = jdbc.read(statement);
        return result.getEntity();
    }

    @Override
    public Client save(Client client) {
        FetchEntityById<Client> function = this::getEntityById;
        SqlCreateStatement<Client> statement = ClientSqlStatement.save(client, function);
        SqlCreateResult<Client> result = jdbc.save(statement);
        return result.getEntity();
    }
}
