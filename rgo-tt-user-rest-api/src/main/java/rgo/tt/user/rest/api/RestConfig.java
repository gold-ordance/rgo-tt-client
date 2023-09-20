package rgo.tt.user.rest.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.user.rest.api.client.RestClientService;
import rgo.tt.user.rest.api.client.RestClientServiceImpl;
import rgo.tt.user.service.ServiceConfig;
import rgo.tt.user.service.client.ClientService;

@Configuration
@Import(ServiceConfig.class)
public class RestConfig {

    @Bean
    public RestClientService restClientService(ClientService service) {
        return new RestClientServiceImpl(service);
    }
}
