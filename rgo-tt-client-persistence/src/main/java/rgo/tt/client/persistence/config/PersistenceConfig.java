package rgo.tt.client.persistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.client.persistence.storage.repository.client.ClientRepository;
import rgo.tt.client.persistence.storage.repository.client.PostgresClientRepository;
import rgo.tt.client.persistence.storage.repository.client.TxClientRepositoryDecorator;
import rgo.tt.common.persistence.DbTxManager;
import rgo.tt.common.persistence.StatementJdbcTemplateAdapter;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionHandler;
import rgo.tt.common.persistence.translator.UniqueViolationPostgresH2ExceptionHandler;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Import(DbDialectConfig.class)
public class PersistenceConfig {

    @Autowired private DataSource dataSource;

    @Bean
    public DbTxManager txManager() {
        return new DbTxManager(dataSource);
    }

    @Bean
    public UniqueViolationPostgresH2ExceptionHandler uniqueViolationHandler() {
        return new UniqueViolationPostgresH2ExceptionHandler();
    }

    @Bean
    public StatementJdbcTemplateAdapter jdbc(List<PostgresH2ExceptionHandler> handlers) {
        return new StatementJdbcTemplateAdapter(txManager(), handlers);
    }

    @Bean
    public ClientRepository clientRepository(StatementJdbcTemplateAdapter jdbc) {
        ClientRepository repository = new PostgresClientRepository(jdbc);
        return new TxClientRepositoryDecorator(repository, txManager());
    }
}
