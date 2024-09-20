package nl.mtbparts.domotics.mdns;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

@Slf4j
@ApplicationScoped
public class EnergyServiceListener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent event) {
        log.info("--- Service added: {}", event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        log.info("--- Service removed: {}", event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        log.info("--- Service resolved: {}", event.getInfo());
    }
}