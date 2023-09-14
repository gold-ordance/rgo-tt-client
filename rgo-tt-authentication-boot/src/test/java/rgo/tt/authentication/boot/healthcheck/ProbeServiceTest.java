package rgo.tt.authentication.boot.healthcheck;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static rgo.tt.authentication.boot.healthcheck.ProbeService.fail;
import static rgo.tt.authentication.boot.healthcheck.ProbeService.ok;

class ProbeServiceTest {

    private final ProbeService service = new ProbeService();

    @Test
    void getLivenessProbe_ok() {
        HttpResponse expected = ok();
        HttpResponse actual = service.getLivenessProbe();
        assertThat(status(expected)).isEqualTo(status(actual));
    }

    @Test
    void getReadinessProbe_fail_contextIsNotReady() {
        HttpResponse expected = fail();
        HttpResponse actual = service.getReadinessProbe();
        assertThat(status(expected)).isEqualTo(status(actual));
    }

    @Test
    void getReadinessProbe_ok() {
        service.start(any());

        HttpResponse expected = ok();
        HttpResponse actual = service.getReadinessProbe();
        assertThat(status(expected)).isEqualTo(status(actual));
    }

    private HttpStatus status(HttpResponse response) {
        return response.aggregate().join().status();
    }
}
