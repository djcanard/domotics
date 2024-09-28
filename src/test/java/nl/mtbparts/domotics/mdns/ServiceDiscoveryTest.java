package nl.mtbparts.domotics.mdns;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import io.quarkus.test.component.TestConfigProperty;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javax.jmdns.JmDNS;

import static org.mockito.Mockito.inOrder;

@QuarkusComponentTest
@TestConfigProperty(key="service-discovery.service-types[0]", value="my.service.1")
@TestConfigProperty(key="service-discovery.service-types[1]", value="my.service.2")
class ServiceDiscoveryTest {

    @Inject
    ServiceDiscovery serviceDiscovery;

    @Inject
    ServiceListener serviceListener;

    @InjectMock
    JmDNS jmDNS;

    @Test
    void shouldStart() {
        serviceDiscovery.start();

        InOrder inOrder = inOrder(jmDNS);
        inOrder.verify(jmDNS).addServiceListener("my.service.1", serviceListener);
        inOrder.verify(jmDNS).addServiceListener("my.service.2", serviceListener);
    }

    @Test
    void shouldStop() {
        serviceDiscovery.stop();

        InOrder inOrder = inOrder(jmDNS);
        inOrder.verify(jmDNS).removeServiceListener("my.service.1", serviceListener);
        inOrder.verify(jmDNS).removeServiceListener("my.service.2", serviceListener);
    }
}