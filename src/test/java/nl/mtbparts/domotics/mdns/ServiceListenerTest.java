package nl.mtbparts.domotics.mdns;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;

import static org.mockito.Mockito.*;

@QuarkusComponentTest
class ServiceListenerTest {

    @Inject
    ServiceListener serviceListener;

    @InjectMock
    EventBus eventBus;

    @Test
    void shouldSendAddedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app0");
        ServiceEvent serviceEvent = createServiceEvent(serviceInfo);

        serviceListener.serviceAdded(serviceEvent);

        verify(eventBus).send("app0.added", serviceInfo);
    }

    @Test
    void shouldSendResolvedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app1");
        ServiceEvent serviceEvent = createServiceEvent(serviceInfo);

        serviceListener.serviceResolved(serviceEvent);

        verify(eventBus).send("app1.resolved", serviceInfo);
    }

    @Test
    void shouldSendRemovedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app2");
        ServiceEvent serviceEvent = createServiceEvent(serviceInfo);

        serviceListener.serviceRemoved(serviceEvent);

        verify(eventBus).send("app2.removed", serviceInfo);
    }

    private static ServiceInfo createServiceInfo(String application) {
        ServiceInfo serviceInfo = mock(ServiceInfo.class);
        when(serviceInfo.getApplication()).thenReturn(application);
        return serviceInfo;
    }

    private static ServiceEvent createServiceEvent(ServiceInfo serviceInfo) {
        ServiceEvent serviceEvent = mock(ServiceEvent.class);
        when(serviceEvent.getInfo()).thenReturn(serviceInfo);
        return serviceEvent;
    }
}