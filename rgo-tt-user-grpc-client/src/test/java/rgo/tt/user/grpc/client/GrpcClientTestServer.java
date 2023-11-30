package rgo.tt.user.grpc.client;

import io.grpc.stub.StreamObserver;
import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;
import rgo.tt.user.grpc.ClientServiceGrpc;

import java.util.concurrent.TimeUnit;

class GrpcClientTestServer extends ClientServiceGrpc.ClientServiceImplBase {

    private static final long PAUSE_MILLIS = 10L;

    @Override
    public void findByUsername(ClientGetByUsernameRequest rq, StreamObserver<ClientGetEntityResponse> responseObserver) {
        sleep();
        responseObserver.onNext(createGetResponse(rq));
        responseObserver.onCompleted();
    }

    private static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(PAUSE_MILLIS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClientGetEntityResponse createGetResponse(ClientGetByUsernameRequest rq) {
        return ClientGetEntityResponse.newBuilder()
                .setUsername(rq.getUsername())
                .build();
    }
}
