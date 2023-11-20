package rgo.tt.user.grpc.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rgo.tt.user.grpc.service.client.GrpcClientService;
import rgo.tt.user.service.ServiceConfig;
import rgo.tt.user.service.client.ClientService;

@Configuration
@Import(ServiceConfig.class)
public class GrpcConfig {

    @Bean
    public GrpcClientService grpcClientService(ClientService service) {
        return new GrpcClientService(service);
    }
}
