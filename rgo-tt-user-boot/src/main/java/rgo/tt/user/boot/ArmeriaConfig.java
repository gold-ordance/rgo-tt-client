package rgo.tt.user.boot;

import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.common.armeria.config.ArmeriaCommonConfig;
import rgo.tt.common.armeria.service.HeadersService;
import rgo.tt.common.armeria.service.ProbeService;
import rgo.tt.user.rest.api.client.RestClientService;

import java.util.function.Function;

@Configuration
@Import(ArmeriaCommonConfig.class)
public class ArmeriaConfig {

    @Autowired private RestClientService restClientService;

    @Autowired private ProbeService probeService;

    @Autowired private Function<? super HttpService, CorsService> corsDecorator;
    @Autowired private Function<? super HttpService, HeadersService> headersDecorator;

    @Bean
    public ArmeriaServerConfigurator armeriaConfigurator() {
        return serverBuilder ->
                serverBuilder
                        .annotatedService("/internal", probeService)
                        .annotatedService("/clients", restClientService)
                        .serviceUnder("/docs", docService())
                        .decorator(LoggingService.newDecorator())
                        .decorator(headersDecorator)
                        .decorator(corsDecorator);
    }

    private DocService docService() {
        return DocService.builder().build();
    }
}
