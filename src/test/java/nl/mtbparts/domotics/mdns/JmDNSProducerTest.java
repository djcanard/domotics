package nl.mtbparts.domotics.mdns;

import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.jmdns.JmDNS;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusComponentTest
class JmDNSProducerTest {

    @Inject
    JmDNSProducer jmDNSProducer;

    @Inject
    JmDNS jmDNS;

    @Test
    void shouldProduce() {
        assertThat(jmDNS).isNotNull();
        assertThat(jmDNS.getName()).isEqualTo("homewizard");
    }
}