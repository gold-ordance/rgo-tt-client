package rgo.tt.user.rest.api.client;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.MediaType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.common.rest.api.StatusCode;
import rgo.tt.common.rest.api.utils.SimpleArmeriaServer;
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
import static rgo.tt.common.rest.api.utils.RestUtils.fromJson;
import static rgo.tt.common.rest.api.utils.RestUtils.json;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;
import static rgo.tt.user.rest.api.RequestGenerator.createClientSaveRequest;
import static rgo.tt.user.rest.api.client.ClientMapper.map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestConfig.class)
class RestClientServiceImplTest {

    private static final int PORT = 8081;
    private static final String HOST = "http://127.0.0.1:" + PORT;
    private static SimpleArmeriaServer SERVER;

    @Autowired private RestClientService restService;
    @Autowired private ClientService service;

    private final WebClient client = WebClient.of();

    @BeforeEach
    void setUp() {
        startServer();
        H2PersistenceUtils.truncateTables();
    }

    private void startServer() {
        if (SERVER == null) {
            SERVER = new SimpleArmeriaServer(PORT, restService);
            SERVER.start();
        }
    }

    @AfterAll
    static void afterAll() {
        SERVER.stop();
    }

    @Test
    void findAll() {
        List<ClientDto> expected = insertRandomClients();

        String json = get(HOST);
        ClientGetListResponse response = fromJson(json, ClientGetListResponse.class);
        List<ClientDto> actual = response.getClients();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(expected).containsExactlyInAnyOrderElementsOf(actual);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeEntityId = randomPositiveLong();

        String json = get(HOST + "/" + fakeEntityId);
        ClientGetEntityResponse response = fromJson(json, ClientGetEntityResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.NOT_FOUND);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(actual).isNull();
    }

    @Test
    void findByEntityId_found() {
        ClientDto expected = insert(randomClient());

        String json = get(HOST + "/" + expected.getEntityId());
        ClientGetEntityResponse response = fromJson(json, ClientGetEntityResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.SUCCESS);
        assertThat(response.getStatus().getMessage()).isNull();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void save_emailIsNull() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail(null);

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The email is null.");
        assertThat(actual).isNull();
    }

    @Test
    void save_emailIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail("");

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The email is empty.");
        assertThat(actual).isNull();
    }

    @Test
    void save_passwordIsNull() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword(null);

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is null.");
        assertThat(actual).isNull();
    }

    @Test
    void save_passwordIsEmpty() {
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setPassword("");

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_RQ);
        assertThat(response.getStatus().getMessage()).isEqualTo("The password is empty.");
        assertThat(actual).isNull();
    }

    @Test
    void save_emailAlreadyExists() {
        ClientDto saved = insert(randomClient());
        ClientSaveRequest rq = createClientSaveRequest();
        rq.setEmail(saved.getEmail());

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.INVALID_ENTITY);
//        assertThat(response.getStatus().getMessage()).isEqualTo("");
        assertThat(actual).isNull();
    }

    @Test
    void save() {
        ClientSaveRequest rq = createClientSaveRequest();

        String json = post(json(rq));
        ClientModifyResponse response = fromJson(json, ClientModifyResponse.class);
        ClientDto actual = response.getClient();

        assertThat(response.getStatus().getStatusCode()).isEqualTo(StatusCode.STORED);
        assertThat(rq.getEmail()).isEqualTo(actual.getEmail());
        assertThat(actual.getEntityId()).isNotNull();
        assertThat(actual.getCreatedDate()).isNotNull();
        assertThat(actual.getLastModifiedDate()).isNotNull();
    }

    private String get(String url) {
        return client.get(url)
                .aggregate()
                .join()
                .contentUtf8();
    }

    private String post(String content) {
        return client.prepare()
                .post(HOST)
                .content(MediaType.JSON_UTF_8, content)
                .execute()
                .aggregate()
                .join()
                .contentUtf8();
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
