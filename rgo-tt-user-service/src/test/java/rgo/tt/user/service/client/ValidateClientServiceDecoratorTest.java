package rgo.tt.user.service.client;

import org.junit.jupiter.api.Test;
import rgo.tt.common.validator.ValidateException;
import rgo.tt.user.persistence.storage.entity.Client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.user.persistence.storage.utils.EntityGenerator.randomClientBuilder;

class ValidateClientServiceDecoratorTest {

    private final ValidateClientServiceDecorator service;

    {
        ClientService mocked = mock(ClientService.class);
        service = new ValidateClientServiceDecorator(mocked);
    }

    @Test
    void findById_invalidRq_entityIdIsNull() {
        assertThatThrownBy(() -> service.findByEntityId(null))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The entityId is null.");
    }

    @Test
    void findById_invalidRq_entityIdIsNegative() {
        long negativeEntityId = -randomPositiveLong();
        assertThatThrownBy(() -> service.findByEntityId(negativeEntityId))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The entityId is negative.");
    }

    @Test
    void save_invalidRq_emailIsNull() {
        Client client = randomClientBuilder()
                .setEmail(null)
                .build();

        assertThatThrownBy(() -> service.save(client))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The email is null.");
    }

    @Test
    void save_invalidRq_emailIsEmpty() {
        Client client = randomClientBuilder()
                .setEmail("")
                .build();

        assertThatThrownBy(() -> service.save(client))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The email is empty.");
    }

    @Test
    void save_invalidRq_passwordIsNull() {
        Client client = randomClientBuilder()
                .setPassword(null)
                .build();

        assertThatThrownBy(() -> service.save(client))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The password is null.");
    }

    @Test
    void save_invalidRq_passwordIsEmpty() {
        Client client = randomClientBuilder()
                .setPassword("")
                .build();

        assertThatThrownBy(() -> service.save(client))
                .isInstanceOf(ValidateException.class)
                .hasMessage("The password is empty.");
    }
}
