package nl.mtbparts.domotics.mdns;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

public class JmDNSProducer {

    @Produces
    @ApplicationScoped
    JmDNS jmDNS() throws IOException {
        return JmDNS.create(InetAddress.getLocalHost(), "homewizard");
    }
}
