package rgo.tt.user.persistence.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.common.persistence.DbProperties;

import javax.sql.DataSource;

import static rgo.tt.user.persistence.storage.utils.H2PersistenceUtils.h2Source;
import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.hikariSource;

@Configuration
public class DbDialectConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "persistence", name = "dialect", havingValue = "H2", matchIfMissing = true)
    public static class H2Config {

        @Bean
        public DataSource h2() {
            return h2Source();
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "persistence", name = "dialect", havingValue = "POSTGRES")
    public static class PostgresConfig {

        @Bean
        @ConfigurationProperties("persistence")
        public DbProperties dbProperties() {
            return new DbProperties();
        }

        @Bean
        public DataSource pg() {
            DbProperties properties = dbProperties();
            return hikariSource(properties);
        }
    }
}
