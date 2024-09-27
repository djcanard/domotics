package nl.mtbparts.domotics.mdns;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.test.HomewizardTestProfile;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import javax.jmdns.JmDNS;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;

@QuarkusTest
@TestProfile(HomewizardTestProfile.class)
class ServiceDiscoveryTest {

    @Inject
    ServiceDiscovery serviceDiscovery;

    @InjectMock
    JmDNS jmDNS;

    @Test
    void shouldStart() {
        serviceDiscovery.start();

        InOrder inOrder = inOrder(jmDNS);
        inOrder.verify(jmDNS).addServiceListener(eq("my.service.1"), any(ServiceListener.class));
        inOrder.verify(jmDNS).addServiceListener(eq("my.service.2"), any(ServiceListener.class));
    }

    @Test
    void shouldStop() {
        serviceDiscovery.stop();

        InOrder inOrder = inOrder(jmDNS);
        inOrder.verify(jmDNS).removeServiceListener(eq("my.service.1"), any(ServiceListener.class));
        inOrder.verify(jmDNS).removeServiceListener(eq("my.service.2"), any(ServiceListener.class));
    }

}