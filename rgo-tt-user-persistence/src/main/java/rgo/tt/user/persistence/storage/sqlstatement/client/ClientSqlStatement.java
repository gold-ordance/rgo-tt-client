package rgo.tt.user.persistence.storage.sqlstatement.client;

import org.springframework.jdbc.core.RowMapper;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.common.persistence.sqlstatement.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;

public final class ClientSqlStatement {

    private ClientSqlStatement() {
    }

    public static SqlReadStatement<Client> findAll() {
        SqlRequest request = ClientSqlRequest.findAll();
        return SqlReadStatement.from(request, CLIENT_ROW_MAPPER);
    }

    public static SqlReadStatement<Client> findByEntityId(Long entityId) {
        SqlRequest request = ClientSqlRequest.findByEntityId(entityId);
        return SqlReadStatement.from(request, CLIENT_ROW_MAPPER);
    }

    public static SqlCreateStatement<Client> save(Client client, FetchEntityById<Client> function) {
        SqlRequest request = ClientSqlRequest.save(client);
        return SqlCreateStatement.from(request, function);
    }

    private static final RowMapper<Client> CLIENT_ROW_MAPPER = (rs, rowNum) -> Client.builder()
            .setEntityId(rs.getLong("entity_id"))
            .setEmail(rs.getString("email"))
            .setPassword(rs.getString("password"))
            .setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime())
            .setLastModifiedDate(rs.getTimestamp("last_modified_date").toLocalDateTime())
            .build();
}
