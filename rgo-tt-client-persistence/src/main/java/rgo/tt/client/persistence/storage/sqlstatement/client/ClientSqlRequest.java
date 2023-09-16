package rgo.tt.client.persistence.storage.sqlstatement.client;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import rgo.tt.client.persistence.storage.entity.Client;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;

public final class ClientSqlRequest {

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
