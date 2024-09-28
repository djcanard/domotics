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
        HomewizardDevice device = HomewizardDevice.of(serviceInfo);

        deviceRepository.add(device);

        if (!device.isApiEnabled()) {
            log.warn("API is not enabled for device: {}", device.getServiceName());
        }

        eventBus.send(device.getDeviceType() + ".resolved", device);
    }

    @ConsumeEvent(value = "hwenergy.removed")
    void onRemoved(ServiceInfo serviceInfo) {
        HomewizardDevice device = HomewizardDevice.of(serviceInfo);

        deviceRepository.remove(device.getDeviceId());

        eventBus.send(device.getDeviceType() + ".removed", device);
    }
}
