package nl.mtbparts.domotics.mdns;

import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.jmdns.ServiceEvent;

@Slf4j
@Singleton
public class ServiceListener implements javax.jmdns.ServiceListener {

    @Inject
    EventBus eventBus;

    @Override
    public void serviceAdded(ServiceEvent event) {
        log.debug("Service added: {}", event.getName());

        eventBus.send(event.getInfo().getApplication() + ".added", event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        log.debug("Service resolved: {}", event.getName());

        eventBus.send(event.getInfo().getApplication() + ".resolved", event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        log.debug("Service removed: {}", event.getName());

        eventBus.send(event.getInfo().getApplication() + ".removed", event.getInfo());
    }
}