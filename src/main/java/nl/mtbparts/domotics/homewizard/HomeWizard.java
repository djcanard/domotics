package nl.mtbparts.domotics.homewizard;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class HomeWizard {

    @Inject
    HomeWizardService homeWizardService;

    @Scheduled(every = "30s")
    public void logMeasurement() {
        log.info("measurement: {}", homeWizardService.measurement());
    }
}
