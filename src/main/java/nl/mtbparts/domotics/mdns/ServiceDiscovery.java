package nl.mtbparts.domotics.mdns;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Slf4j
@Singleton
@Startup
public class ServiceDiscovery {

    @ConfigProperty(name = "service-discovery.enabled", defaultValue = "true")
    boolean serviceDiscoveryEnabled;

    @ConfigProperty(name = "service-discovery.service-types")
    List<String> serviceTypes;

    @Inject
    ServiceListener serviceListener;

    private JmDNS jmDNS;

    @PostConstruct
    void init() {
        if (serviceDiscoveryEnabled) {
            try {
                start();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @PreDestroy
    void destroy() {
        if (serviceDiscoveryEnabled) {
            stop();
        }
    }

    public void start() throws IOException {
        jmDNS = JmDNS.create(InetAddress.getLocalHost(), "homewizard");

        log.info("Service discovery started for: {}", serviceTypes);
        serviceTypes.forEach(type -> jmDNS.addServiceListener(type, serviceListener));
    }

    public void stop() {
        serviceTypes.forEach(type -> jmDNS.removeServiceListener(type, serviceListener));
    }
}