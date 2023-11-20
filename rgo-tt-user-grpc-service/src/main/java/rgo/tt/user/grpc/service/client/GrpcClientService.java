package rgo.tt.user.grpc.service.client;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.grpc.ClientServiceGrpc;
import rgo.tt.user.service.client.ClientService;

import static rgo.tt.user.grpc.service.client.ClientMapper.map;

public class GrpcClientService extends ClientServiceGrpc.ClientServiceImplBase {

    private final ClientService service;

    public GrpcClientService(ClientService service) {
        this.service = service;
    }

    @Override
    public void findByUsername(ClientGetByUsernameRequest request, StreamObserver<ClientGetEntityResponse> responseObserver) {
        service.findByEmail(request.getUsername())
                .ifPresentOrElse(
                        client -> {
                            responseObserver.onNext(map(client));
                            responseObserver.onCompleted();
                        },
                        () -> responseObserver.onError(Status.NOT_FOUND
                                .withDescription("Client not found for username: " + request.getUsername())
                                .asRuntimeException())
                );
    }
}
