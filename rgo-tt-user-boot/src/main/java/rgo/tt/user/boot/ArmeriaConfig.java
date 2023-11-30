package rgo.tt.user.boot;

import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceNaming;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;
import com.linecorp.armeria.server.throttling.ThrottlingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.common.armeria.ArmeriaCommonConfig;
import rgo.tt.common.armeria.ProbeService;
import rgo.tt.common.armeria.headers.HeadersService;
import rgo.tt.user.grpc.service.client.GrpcClientService;
import rgo.tt.user.rest.api.client.RestClientService;

import java.util.function.Function;

@Configuration
@Import(ArmeriaCommonConfig.class)
public class ArmeriaConfig {

    @Autowired private RestClientService restClientService;
    @Autowired private GrpcClientService grpcClientService;

    @Autowired private ProbeService probeService;

    @Autowired private Function<? super HttpService, ThrottlingService> throttlingDecorator;
    @Autowired private Function<? super HttpService, CorsService> corsDecorator;
    @Autowired private Function<? super HttpService, HeadersService> headersDecorator;
    @Autowired private Function<? super HttpService, MetricCollectingService> metricsDecorator;

    @Autowired private PrometheusMeterRegistry registry;

    @Bean
    public ArmeriaServerConfigurator armeriaConfigurator() {
        return serverBuilder ->
                serverBuilder
                        .defaultServiceNaming(ServiceNaming.simpleTypeName())
                        .annotatedService("/internal", probeService)
                        .annotatedService("/clients", restClientService)
                        .service(GrpcService.builder().addService(grpcClientService).useBlockingTaskExecutor(true).build())
                        .serviceUnder("/internal/metrics", PrometheusExpositionService.of(registry.getPrometheusRegistry()))
                        .serviceUnder("/docs", docService())
                        .decorator(LoggingService.builder()
                                .requestLogLevel(LogLevel.INFO)
                                .successfulResponseLogLevel(LogLevel.INFO)
                                .failureResponseLogLevel(LogLevel.WARN)
                                .newDecorator())
                        .decorator(headersDecorator)
                        .decorator(metricsDecorator)
                        .decorator(corsDecorator)
                        .decorator(throttlingDecorator);
    }

    private DocService docService() {
        return DocService.builder().build();
    }
}
