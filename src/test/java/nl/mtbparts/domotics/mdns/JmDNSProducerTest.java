package nl.mtbparts.domotics.mdns;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.jmdns.JmDNS;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class JmDNSProducerTest {

    @Inject
    JmDNS jmDNS;

    @Test
    void shouldInjectJmDNS() {
        assertThat(jmDNS).isNotNull();
    }
}