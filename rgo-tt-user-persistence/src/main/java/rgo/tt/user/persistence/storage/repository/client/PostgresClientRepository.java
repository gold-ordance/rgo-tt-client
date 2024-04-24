package rgo.tt.user.persistence.storage.repository.client;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlstatement.FetchEntityById;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.user.persistence.storage.entity.Client;

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

    private static final class ClientSqlStatement {

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

        public static SqlReadStatement<Client> findByEmail(String email) {
            SqlRequest request = ClientSqlRequest.findByEmail(email);
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

    private static final class ClientSqlRequest {

        private static final String TABLE_NAME = "client";

        private ClientSqlRequest() {
        }

        public static SqlRequest findAll() {
            return new SqlRequest(select());
        }

        public static SqlRequest findByEntityId(Long entityId) {
            String request = select() + "WHERE entity_id = :entity_id";
            MapSqlParameterSource params = new MapSqlParameterSource("entity_id", entityId);
            return new SqlRequest(request, params);
        }

        public static SqlRequest findByEmail(String email) {
            String request = select() + "WHERE email = :email";
            MapSqlParameterSource params = new MapSqlParameterSource("email", email);
            return new SqlRequest(request, params);
        }

        public static SqlRequest save(Client client) {
            String request = """
                INSERT INTO %s(email, password)
                VALUES(:email, :password)
                """.formatted(TABLE_NAME);

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("email", client.getEmail())
                    .addValue("password", client.getPassword());

            return new SqlRequest(request, params);
        }

        private static String select() {
            return """
                SELECT entity_id,
                       email,
                       password,
                       created_date,
                       last_modified_date
                  FROM %s
                """.formatted(TABLE_NAME);
        }
    }
}
