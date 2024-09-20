package nl.mtbparts.domotics;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.homewizard.HomeWizardService;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.mdns.EnergyServiceDiscovery;

@Slf4j
@ApplicationScoped
@Startup
public class Application {

    @Inject
    HomeWizardService homeWizardService;

    @Inject
    EnergyServiceDiscovery energyServiceDiscovery;

    @PostConstruct
    void init() {
        log();
//        try {
//            energyServiceDiscovery.discover();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
    }

    private void log() {
        BasicResponse basicResponse = homeWizardService.basic();
        log.info("--- {} ({} firmware {}) API {} ---", basicResponse.getProductName(), basicResponse.getProductType(), basicResponse.getFirmwareVersion(), basicResponse.getApiVersion());
    }
}
