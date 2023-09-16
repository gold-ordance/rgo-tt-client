package rgo.tt.client.boot;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rgo.tt.client.boot.healthcheck.ProbeService;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ProbeService probeService() {
        return new ProbeService();
    }

    @Bean
    public ArmeriaServerConfigurator armeriaConfigurator() {
        return serverBuilder ->
                serverBuilder
                        .annotatedService("/internal", probeService())
                        .serviceUnder("/docs", docService());
    }

    private DocService docService() {
        return DocService.builder().build();
    }
}
