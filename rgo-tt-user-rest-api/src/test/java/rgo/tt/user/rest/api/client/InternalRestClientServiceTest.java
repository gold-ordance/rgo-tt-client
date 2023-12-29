package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.ErrorResponse;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.persistence.storage.utils.EntityGenerator;
import rgo.tt.user.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.user.rest.api.RestConfig;
import rgo.tt.user.rest.api.client.dto.ClientDto;
import rgo.tt.user.rest.api.client.request.ClientSaveRequest;
import rgo.tt.user.rest.api.client.response.ClientGetEntityResponse;
import rgo.tt.user.rest.api.client.response.ClientGetListResponse;
import rgo.tt.user.rest.api.client.response.ClientModifyResponse;
import rgo.tt.user.service.client.ClientService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.armeria.rest.RestUtils.fromJson;
import static rgo.tt.common.armeria.rest.RestUtils.json;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.get;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaClientManager.post;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.startArmeriaServer;
import static rgo.tt.common.armeria.test.simpleserver.ArmeriaServerManager.stopServer;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;
import static rgo.tt.user.rest.api.client.ClientMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class InternalRestClientServiceTest {

    @Autowired private RestClientService restService;
    @Autowired private ClientService service;

    @BeforeEach
    void setUp() {
        startArmeriaServer(restService);
        H2PersistenceUtils.truncateTables();
    }

    @AfterAll
    static void afterAll() {
        stopServer();
    }

    @Test
    void findAll() {
        List<ClientDto> expected = insertRandomClients();

        String json = get();
        ClientGetListResponse response = fromJson(json, ClientGetListResponse.class);
        List<ClientDto> actual = response.getClients();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeEntityId = randomPositiveLong();

        String json = get("/" + fakeEntityId);
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
        assertThat(response.getStatus().getMessage()).isNull();
    }

    @Test
    void findByEntityId_found() {
        ClientDto expected = insert(randomClient());

        String json = get("/" + expected.getEntityId());
        ClientGetEntityResponse response = fromJson(json, ClientGetEntityResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void save_emailAlreadyExists() {
        ClientDto saved = insert(randomClient());
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail(saved.getEmail());

        String json = post(json(rq));
        ErrorResponse response = fromJson(json, ErrorResponse.class);

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
        assertThat(response.getStatus().getMessage()).isNotNull();
    }

    @Test
    void save() {
        ClientSaveRequest rq = createClientSaveRequest();

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.STORED);
        assertThat(actual.getEmail()).isEqualTo(rq.getEmail());
        assertThat(actual.getEntityId()).isNotNull();
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getLastModifiedDate()).isNotNull();
    }

    private List<ClientDto> insertRandomClients() {
        int limit = ThreadLocalRandom.current().nextInt(1, 10);
        return Stream.generate(EntityGenerator::randomClient)
                .limit(limit)
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private ClientDto insert(Client task) {
        return map(service.save(task));
    }
}
