package nl.mtbparts.domotics.mdns;

import lombok.extern.slf4j.Slf4j;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ServiceLogger {

    public static void log(ServiceEvent serviceEvent) {
        log.info("event: {} - {}", serviceEvent.getType(), serviceEvent.getName());
//        log.info("event source: {}", serviceEvent.getSource());
    }

    public static void log(ServiceInfo serviceInfo) {
        if (log.isInfoEnabled()) {
            log.info("service info app      : {}", serviceInfo.getApplication());
            log.info("service info name     : {}", serviceInfo.getName());
//        log.info("service info nice     : {}", serviceInfo.getNiceTextString());
//        log.info("service info qname    : {}", serviceInfo.getQualifiedName());
//        log.info("service info server   : {}", serviceInfo.getServer());
//        log.info("service info domain   : {}", serviceInfo.getDomain());
            log.info("service info host addr: {}", (Object) serviceInfo.getHostAddresses());
            log.info("service info protocol : {}", serviceInfo.getProtocol());
            log.info("service info port     : {}", serviceInfo.getPort());
//        log.info("service info key      : {}", serviceInfo.getKey());
//        log.info("service info type     : {}", serviceInfo.getType());
//        log.info("service info subtype  : {}", serviceInfo.getSubtype());
//        log.info("service info type sub : {}", serviceInfo.getTypeWithSubtype());
//        log.info("service info priority : {}", serviceInfo.getPriority());
//        log.info("service info weight   : {}", serviceInfo.getWeight());
            List<String> properties = Collections.list(serviceInfo.getPropertyNames());
            log.info("service info props    : {}", properties);
            properties.forEach(p -> log.info("service info prop[{}] : {}", p, serviceInfo.getPropertyString(p)));
//        log.info("service info textbytes: {}", new String(serviceInfo.getTextBytes()));
//        log.info("service info inet4    : {}", (Object) serviceInfo.getInet4Addresses());
//        log.info("service info inet6    : {}", (Object) serviceInfo.getInet6Addresses());
        }
    }
}
