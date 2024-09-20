package nl.mtbparts.domotics.mdns;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

@Slf4j
@ApplicationScoped
public class EnergyServiceDiscovery {

    @Inject
    EnergyServiceListener energyServiceListener;

    private JmDNS jmDNS;

    public void discover() throws IOException {
        jmDNS = JmDNS.create(InetAddress.getByName("192.168.68.67"));
//        jmDNS = JmDNS.create(InetAddress.getLocalHost(), "myname");
        jmDNS.addServiceListener("_hwenergy._tcp.", energyServiceListener);
    }
}