package nl.mtbparts.domotics.mdns;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Singleton
@Startup
public class ServiceDiscovery {

    @ConfigProperty(name = "service-discovery.enabled", defaultValue = "true")
    boolean serviceDiscoveryEnabled;

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

        jmDNS.addServiceListener(ServiceType.HWENERGY.getValue(), serviceListener);
        jmDNS.addServiceListener(ServiceType.GOOGLECAST.getValue(), serviceListener);
    }

    public void stop() {
        jmDNS.removeServiceListener(ServiceType.HWENERGY.getValue(), serviceListener);
        jmDNS.removeServiceListener(ServiceType.GOOGLECAST.getValue(), serviceListener);
    }

    public List<ServiceInfo> listServices(ServiceType serviceType) {
        log.info("Listing devices for {}", serviceType);

        List<ServiceInfo> serviceInfos = Arrays.stream(jmDNS.list(serviceType.getValue(), 3000)).toList();

        log.info("Devices found: {}", serviceInfos.size());
        serviceInfos.forEach(serviceInfo -> log.info("serviceInfo: {}", serviceInfo));

        return serviceInfos;
    }
}