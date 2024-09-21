package nl.mtbparts.domotics.homewizard.device;

import io.quarkus.scheduler.Scheduler;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.homewizard.api.ApiProvider;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;

@Slf4j
@ApplicationScoped
public class P1MeterConsumer {

    @Inject
    ApiProvider apiProvider;

    @Inject
    Scheduler scheduler;

    /**
     * When api availability is toggled, we get a resolved event
     */
    @ConsumeEvent(value = "HWE-P1.resolved", blocking = true)
    void onResolved(HomewizardDeviceInfo deviceInfo) {
        log.info("P1 Meter device resolved: {}", deviceInfo);

        logBasic(deviceInfo);
        scheduleMeasurement(deviceInfo);
    }

    @ConsumeEvent(value = "HWE-P1.removed")
    void onRemoved(HomewizardDeviceInfo deviceInfo) {
        log.info("P1 Meter device removed: {}", deviceInfo);

        unscheduleMeasurement(deviceInfo);
    }

    private void logBasic(HomewizardDeviceInfo deviceInfo) {
        if (deviceInfo.isApiEnabled()) {
            BasicResponse response = apiProvider.basic(deviceInfo.getHost(), deviceInfo.getPort()).request();
            log.info("basic: {}", response);
        }
    }
    public void logMeasurement(HomewizardDeviceInfo deviceInfo) {
//        if (deviceInfo.isApiEnabled()) {
            try {
                MeasurementResponse response = apiProvider.measurement(deviceInfo.getHost(), deviceInfo.getPort()).request();
                log.info("measurement: {}", response);
            } catch (WebApplicationException e) {
                log.error(e.getMessage(), e);
                if (e.getResponse().getStatus() == 403) {
                    unscheduleMeasurement(deviceInfo);
                }
            }
//        }
    }

    private void scheduleMeasurement(HomewizardDeviceInfo deviceInfo) {
        if (scheduler.getScheduledJob(deviceInfo.getName()) != null) {
            unscheduleMeasurement(deviceInfo);
        }
        scheduler.newJob(deviceInfo.getName())
                .setInterval("${homewizard.p1.measurement.schedule.interval}")
                .setTask(executionContext -> logMeasurement(deviceInfo))
                .schedule();
    }

    private void unscheduleMeasurement(HomewizardDeviceInfo deviceInfo) {
        scheduler.unscheduleJob(deviceInfo.getName());
    }
}
