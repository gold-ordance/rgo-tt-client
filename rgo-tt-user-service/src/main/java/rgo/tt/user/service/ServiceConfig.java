package rgo.tt.user.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.user.persistence.config.PersistenceConfig;
import rgo.tt.user.persistence.storage.repository.client.ClientRepository;
import rgo.tt.user.service.client.ClientService;
import rgo.tt.user.service.client.InternalClientService;

@Configuration
@Import(PersistenceConfig.class)
public class ServiceConfig {

    @Bean
    public ClientService tasksBoardService(ClientRepository repository) {
        return new InternalClientService(repository);
    }
}
