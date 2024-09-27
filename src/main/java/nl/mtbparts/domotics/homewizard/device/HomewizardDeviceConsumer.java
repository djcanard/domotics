package nl.mtbparts.domotics.homewizard.device;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.device.DeviceRepository;

import javax.jmdns.ServiceInfo;

@Slf4j
@ApplicationScoped
public class HomewizardDeviceConsumer {

    @Inject
    EventBus eventBus;

    @Inject
    DeviceRepository deviceRepository;

    @ConsumeEvent(value = "hwenergy.added")
    void onAdded(ServiceInfo serviceInfo) {
        log.debug("Homewizard device added: {}", serviceInfo.getName());
    }

    @ConsumeEvent(value = "hwenergy.resolved")
    void onResolved(ServiceInfo serviceInfo) {
        HomewizardDeviceInfo deviceInfo = HomewizardDeviceInfo.of(serviceInfo);

        deviceRepository.add(deviceInfo);

        if (!deviceInfo.isApiEnabled()) {
            log.warn("API is not enabled for device: {}", deviceInfo.getName());
        }

        eventBus.publish(deviceInfo.getProductType().getValue() + ".resolved", deviceInfo);
    }

    @ConsumeEvent(value = "hwenergy.removed")
    void onRemoved(ServiceInfo serviceInfo) {
        HomewizardDeviceInfo deviceInfo = HomewizardDeviceInfo.of(serviceInfo);

        deviceRepository.remove(deviceInfo);

        eventBus.publish(deviceInfo.getProductType().getValue() + ".removed", deviceInfo);
    }
}
