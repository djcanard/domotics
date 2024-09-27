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
        GoogleCastDevice device = GoogleCastDevice.of(serviceInfo);

        deviceRepository.add(device);

        ServiceLogger.log(serviceInfo);

        if (GoogleCastModelName.isKnown(device.getDeviceType())) {
            eventBus.publish(device.getDeviceType() + ".resolved", device);
        } else {
            eventBus.publish("Generic Google Cast.resolved", device);
        }
    }

    @ConsumeEvent(value = "googlecast.removed")
    void onRemoved(ServiceInfo serviceInfo) {
        GoogleCastDevice device = GoogleCastDevice.of(serviceInfo);

        deviceRepository.remove(device);

        ServiceLogger.log(serviceInfo);

        if (GoogleCastModelName.isKnown(device.getDeviceType())) {
            eventBus.publish(device.getDeviceType() + ".removed", device);
        } else {
            eventBus.publish("Generic Google Cast.removed", device);
        }
    }
}
