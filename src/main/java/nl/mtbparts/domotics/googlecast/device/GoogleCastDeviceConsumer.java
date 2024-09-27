package nl.mtbparts.domotics.googlecast.device;

import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.device.DeviceRepository;
import nl.mtbparts.domotics.mdns.ServiceLogger;

import javax.jmdns.ServiceInfo;

@Slf4j
@ApplicationScoped
public class GoogleCastDeviceConsumer {

    @Inject
    EventBus eventBus;

    @Inject
    DeviceRepository deviceRepository;

    @ConsumeEvent(value = "googlecast.added")
    void onAdded(ServiceInfo serviceInfo) {
        log.debug("Google Cast device added: {}", serviceInfo.getName());
    }

    @ConsumeEvent(value = "googlecast.resolved")
    void onResolved(ServiceInfo serviceInfo) {
        GoogleCastDeviceInfo deviceInfo = GoogleCastDeviceInfo.of(serviceInfo);

        deviceRepository.add(deviceInfo);

        ServiceLogger.log(serviceInfo);
//        eventBus.publish(deviceInfo.getProductType().getValue() + ".resolved", deviceInfo);
    }

    @ConsumeEvent(value = "googlecast.removed")
    void onRemoved(ServiceInfo serviceInfo) {
        GoogleCastDeviceInfo deviceInfo = GoogleCastDeviceInfo.of(serviceInfo);

        deviceRepository.remove(deviceInfo);

//        eventBus.publish(deviceInfo.getProductType().getValue() + ".removed", deviceInfo);
    }
}
