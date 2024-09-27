package nl.mtbparts.domotics.mdns;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.jmdns.JmDNS;
import java.util.List;

@Slf4j
@ApplicationScoped
@Startup
public class ServiceDiscovery {

    @ConfigProperty(name = "service-discovery.enabled", defaultValue = "true")
    boolean serviceDiscoveryEnabled;

    @ConfigProperty(name = "service-discovery.service-types")
    List<String> serviceTypes;

    @Inject
    ServiceListener serviceListener;

    @Inject
    JmDNS jmDNS;

    @PostConstruct
    void init() {
        if (serviceDiscoveryEnabled) {
            start();
        }
    }

    @PreDestroy
    void destroy() {
        if (serviceDiscoveryEnabled) {
            stop();
        }
    }

    public void start() {
        log.info("Service discovery started for: {}", serviceTypes);
        serviceTypes.forEach(type -> jmDNS.addServiceListener(type, serviceListener));
    }

    public void stop() {
        serviceTypes.forEach(type -> jmDNS.removeServiceListener(type, serviceListener));
    }
}