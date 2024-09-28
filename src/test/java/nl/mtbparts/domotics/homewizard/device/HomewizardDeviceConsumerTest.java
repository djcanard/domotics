package nl.mtbparts.domotics.homewizard.device;

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
class HomewizardDeviceConsumerTest {

    @Inject
    HomewizardDeviceConsumer homewizardDeviceConsumer;

    @InjectMock
    DeviceRepository deviceRepository;

    @InjectMock
    EventBus eventBus;

    @Test
    void shouldDoNothingOnAddedEvent() {
        homewizardDeviceConsumer.onAdded(mock(ServiceInfo.class));

        verifyNoInteractions(deviceRepository);
        verifyNoInteractions(eventBus);
    }

    @Test
    void shouldSendResolvedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app", "product-type", "service-name");

        homewizardDeviceConsumer.onResolved(serviceInfo);

        Device expectedDevice = HomewizardDevice.builder()
                .application("app")
                .serviceName("service-name")
                .productType("product-type")
                .build();

        verify(deviceRepository).add(expectedDevice);
        verify(eventBus).send("product-type.resolved", expectedDevice);
    }

    @Test
    void shouldSendRemovedEvent() {
        ServiceInfo serviceInfo = createServiceInfo("app", "product-type", "service-name");

        homewizardDeviceConsumer.onRemoved(serviceInfo);

        Device expectedDevice = HomewizardDevice.builder()
                .application("app")
                .serviceName("service-name")
                .productType("product-type")
                .build();

        verify(deviceRepository).remove("service-name");
        verify(eventBus).send("product-type.removed", expectedDevice);
    }


    private static ServiceInfo createServiceInfo(String application, String productType, String serviceName) {
        ServiceInfo serviceInfo = mock(ServiceInfo.class);
        when(serviceInfo.getApplication()).thenReturn(application);
        when(serviceInfo.getName()).thenReturn(serviceName);
        when(serviceInfo.getHostAddresses()).thenReturn(new String[0]);
        when(serviceInfo.getPropertyString("product_type")).thenReturn(productType);
        return serviceInfo;
    }
}