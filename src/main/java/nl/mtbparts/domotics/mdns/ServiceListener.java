package nl.mtbparts.domotics.mdns;

import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import java.util.Collections;

@Slf4j
@Singleton
public class ServiceListener implements javax.jmdns.ServiceListener {

    @Inject
    EventBus eventBus;

    @Override
    public void serviceAdded(ServiceEvent event) {
        log.debug("Service added: {}", event.getName());
        eventBus.publish(event.getInfo().getApplication() + ".added", event.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        log.debug("Service resolved: {}", event.getName());
//        logServiceEvent(event);

        eventBus.publish(event.getInfo().getApplication() + ".resolved", event.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        log.debug("Service removed: {}", event.getName());
//        logServiceEvent(event);

        eventBus.publish(event.getInfo().getApplication() + ".removed", event.getInfo());
    }

    private void logServiceEvent(ServiceEvent serviceEvent) {
        log.debug("event: {} - {}", serviceEvent.getType(), serviceEvent.getName());
//        log.info("event source: {}", serviceEvent.getSource());
    }

    private void logServiceInfo(ServiceInfo serviceInfo) {
        if (log.isDebugEnabled()) {
            log.debug("service info app      : {}", serviceInfo.getApplication());
            log.debug("service info name     : {}", serviceInfo.getName());
//        log.debug("service info nice     : {}", serviceInfo.getNiceTextString());
//        log.debug("service info qname    : {}", serviceInfo.getQualifiedName());
//        log.debug("service info server   : {}", serviceInfo.getServer());
//        log.debug("service info domain   : {}", serviceInfo.getDomain());
            log.debug("service info host addr: {}", (Object) serviceInfo.getHostAddresses());
            log.debug("service info protocol : {}", serviceInfo.getProtocol());
            log.debug("service info port     : {}", serviceInfo.getPort());
//        log.debug("service info key      : {}", serviceInfo.getKey());
//        log.debug("service info type     : {}", serviceInfo.getType());
//        log.debug("service info subtype  : {}", serviceInfo.getSubtype());
//        log.debug("service info type sub : {}", serviceInfo.getTypeWithSubtype());
//        log.debug("service info priority : {}", serviceInfo.getPriority());
//        log.debug("service info weight   : {}", serviceInfo.getWeight());
            log.debug("service info props    : {}", Collections.list(serviceInfo.getPropertyNames()));
//        log.debug("service info textbytes: {}", new String(serviceInfo.getTextBytes()));
//        log.debug("service info inet4    : {}", (Object) serviceInfo.getInet4Addresses());
//        log.debug("service info inet6    : {}", (Object) serviceInfo.getInet6Addresses());
        }
    }
}