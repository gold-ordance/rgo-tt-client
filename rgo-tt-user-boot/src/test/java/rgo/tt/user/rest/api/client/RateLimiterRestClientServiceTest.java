package rgo.tt.user.rest.api.client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rgo.tt.common.armeria.test.ratelimiter.AbstractRestRateLimiterTest;

import static org.assertj.core.api.Assertions.assertThat;
import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;

@SpringBootTest
@ActiveProfiles("test")
class RateLimiterRestClientServiceTest extends AbstractRestRateLimiterTest {

    private static final int PORT = 8081;
    private static final String BASE_URL = "/clients";

    protected RateLimiterRestClientServiceTest() {
        super(PORT);
    }

    @Test
    void findAll() {
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void findByEntityId() {
        long entityId = randomPositiveLong();
        boolean isRequestRejected = isRequestRejected(() -> get(BASE_URL + "/" + entityId));
        assertThat(isRequestRejected).isTrue();
    }

    @Test
    void save() {
        boolean isRequestRejected = isRequestRejected(() -> post(BASE_URL));
        assertThat(isRequestRejected).isTrue();
    }
}
