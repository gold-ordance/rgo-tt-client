package rgo.tt.user.grpc.client;

import rgo.tt.user.grpc.ClientGetByUsernameRequest;
import rgo.tt.user.grpc.ClientGetEntityResponse;

public interface GrpcClientServiceApi {

    ClientGetEntityResponse findByUsername(ClientGetByUsernameRequest rq);
}
