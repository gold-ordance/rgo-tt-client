package rgo.tt.user.persistence.storage.repository.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rgo.tt.user.persistence.config.PersistenceConfig;
import rgo.tt.user.persistence.storage.entity.Client;
import rgo.tt.user.persistence.storage.utils.EntityGenerator;
import rgo.tt.user.persistence.storage.utils.H2PersistenceUtils;
import rgo.tt.common.exceptions.UniqueViolationException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClient;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfig.class)
class ClientRepositoryTest {

    private static final int CLIENTS_LIMIT = 32;

    @Autowired private ClientRepository repository;

    @BeforeEach
    void setUp() {
        H2PersistenceUtils.truncateTables();
    }

    @Test
    void findAll() {
        List<Client> expected = insertRandomClients();
        List<Client> actual = repository.findAll();
        assertThat(expected).containsExactlyInAnyOrderElementsOf(actual);
    }

    @Test
    void findByEntityId_notFound() {
        long fakeId = randomPositiveLong();
        Optional<Client> actual = repository.findByEntityId(fakeId);
        assertThat(actual).isNotPresent();
    }

    @Test
    void findByEntityId_found() {
        Client created = randomClient();
        Client expected = insert(created);

        Optional<Client> actual = repository.findByEntityId(expected.getEntityId());
        assertThat(actual).isPresent();
        assertThat(expected).isEqualTo(actual.get());
    }

    @Test
    void save() {
        Client expected = randomClient();

        Client actual = repository.save(expected);
        assertThat(expected.getEmail()).isEqualTo(actual.getEmail());
        assertThat(expected.getPassword()).isEqualTo(actual.getPassword());
    }

    @Test
    void save_emailAlreadyExists() {
        Client created = randomClient();
        insert(created);

        assertThatExceptionOfType(UniqueViolationException.class).isThrownBy(() -> insert(created));
    }

    private List<Client> insertRandomClients() {
        int limit = ThreadLocalRandom.current().nextInt(1, CLIENTS_LIMIT);
        return Stream.generate(EntityGenerator::randomClient)
                .limit(limit)
                .map(this::insert)
                .collect(Collectors.toList());
    }

    private Client insert(Client task) {
        return repository.save(task);
    }
}
