package nl.mtbparts.domotics.googlecast.device;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.device.Device;
import nl.mtbparts.domotics.device.DeviceRepository;
import org.junit.jupiter.api.Test;

import javax.jmdns.ServiceInfo;

import static org.mockito.Mockito.*;

@QuarkusComponentTest
class GoogleCastDeviceConsumerTest {

    @Inject
    GoogleCastDeviceConsumer googleCastDeviceConsumer;

    @InjectMock
    DeviceRepository deviceRepository;

    @InjectMock
    EventBus eventBus;

    @Test
    void shouldDoNothingOnAddedEvent() {
        googleCastDeviceConsumer.onAdded(mock(ServiceInfo.class));

        verifyNoInteractions(deviceRepository);
        verifyNoInteractions(eventBus);
    }

    @Test
    void shouldSendResolvedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app", "Chromecast HD", "id");

        googleCastDeviceConsumer.onResolved(serviceInfo);

        Device expectedDevice = GoogleCastDevice.builder()
                .application("app")
                .id("id")
                .modelName("Chromecast HD")
                .build();

        verify(deviceRepository).add(expectedDevice);
        verify(eventBus).send("Chromecast HD.resolved", expectedDevice);
    }

    @Test
    void shouldSendRemovedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app", "Chromecast HD", "id");

        googleCastDeviceConsumer.onRemoved(serviceInfo);

        Device expectedDevice = GoogleCastDevice.builder()
                .application("app")
                .id("id")
                .modelName("Chromecast HD")
                .build();

        verify(deviceRepository).remove("id");
        verify(eventBus).send("Chromecast HD.removed", expectedDevice);
    }

    @Test
    void shouldSendResolvedEventForUnknownDevice() {
        ServiceInfo serviceInfo = createServiceInfo("app", "Cast device XYZ", "id");

        googleCastDeviceConsumer.onResolved(serviceInfo);

        Device expectedDevice = GoogleCastDevice.builder()
                .application("app")
                .id("id")
                .modelName("Cast device XYZ")
                .build();

        verify(deviceRepository).add(expectedDevice);
        verify(eventBus).send("Generic Google Cast.resolved", expectedDevice);
    }

    @Test
    void shouldSendRemovedEventForUnknownDevice() {
        ServiceInfo serviceInfo = createServiceInfo("app", "Cast device XYZ", "id");

        googleCastDeviceConsumer.onRemoved(serviceInfo);

        Device expectedDevice = GoogleCastDevice.builder()
                .application("app")
                .id("id")
                .modelName("Cast device XYZ")
                .build();

        verify(deviceRepository).remove("id");
        verify(eventBus).send("Generic Google Cast.removed", expectedDevice);
    }

    @Test
    void shouldNotSendResolvedEventForMissingDeviceType() {
        ServiceInfo serviceInfo = createServiceInfo("app", null, "id");

        googleCastDeviceConsumer.onResolved(serviceInfo);

        Device expectedDevice = GoogleCastDevice.builder()
                .application("app")
                .id("id")
                .modelName(null)
                .build();

        verify(deviceRepository).add(expectedDevice);
        verifyNoInteractions(eventBus);
    }

    @Test
    void shouldSendRemovedEventForMissingDeviceType() {
        ServiceInfo serviceInfo = createServiceInfo("app", null, "id");

        googleCastDeviceConsumer.onRemoved(serviceInfo);

        verify(deviceRepository).remove("id");
        verifyNoInteractions(eventBus);
    }


    private static ServiceInfo createServiceInfo(String application, String modelname, String id) {
        ServiceInfo serviceInfo = mock(ServiceInfo.class);
        when(serviceInfo.getApplication()).thenReturn(application);
        when(serviceInfo.getHostAddresses()).thenReturn(new String[0]);
        when(serviceInfo.getPropertyString("md")).thenReturn(modelname);
        when(serviceInfo.getPropertyString("id")).thenReturn(id);
        return serviceInfo;
    }
}