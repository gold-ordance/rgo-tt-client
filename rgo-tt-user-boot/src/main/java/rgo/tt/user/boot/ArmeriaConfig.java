package rgo.tt.user.boot;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.user.boot.healthcheck.ProbeService;
import rgo.tt.user.rest.api.client.RestClientService;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ProbeService probeService() {
        return new ProbeService();
    }

    @Bean
    public ArmeriaServerConfigurator armeriaConfigurator(RestClientService restClientService) {
        return serverBuilder ->
                serverBuilder
                        .annotatedService("/internal", probeService())
                        .annotatedService("/clients", restClientService)
                        .serviceUnder("/docs", docService())
                        .decorator(LoggingService.newDecorator());
    }

    private DocService docService() {
        return DocService.builder().build();
    }
}
