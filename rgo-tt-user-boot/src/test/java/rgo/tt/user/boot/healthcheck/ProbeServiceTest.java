package rgo.tt.user.boot.healthcheck;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static rgo.tt.user.boot.healthcheck.ProbeService.failure;
import static rgo.tt.user.boot.healthcheck.ProbeService.ok;

class ProbeServiceTest {

    private final ProbeService service = new ProbeService();

    @Test
    void livenessProbe_ok() {
        HttpResponse expected = ok();
        HttpResponse actual = service.livenessProbe();
        assertThat(status(actual)).isEqualTo(status(expected));
    }

    @Test
    void readinessProbe_fail_contextIsNotReady() {
        HttpResponse expected = failure();
        HttpResponse actual = service.readinessProbe();
        assertThat(status(actual)).isEqualTo(status(expected));
    }

    @Test
    void readinessProbe_ok() {
        service.start(any());

        HttpResponse expected = ok();
        HttpResponse actual = service.readinessProbe();
        assertThat(status(actual)).isEqualTo(status(expected));
    }

    private HttpStatus status(HttpResponse response) {
        return response.aggregate().join().status();
    }
}
