package nl.mtbparts.domotics.homewizard.device.p1;

import io.quarkus.scheduler.Scheduler;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.homewizard.api.ApiProvider;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;
import nl.mtbparts.domotics.homewizard.device.HomewizardDeviceInfo;
import nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static nl.mtbparts.domotics.homewizard.measurement.MeasurementConsumer.MEASUREMENT_EVENT;

@Slf4j
@ApplicationScoped
public class P1MeterConsumer {

    @Inject
    ApiProvider apiProvider;

    @Inject
    Scheduler scheduler;

    @Inject
    EventBus eventBus;

    @ConfigProperty(name = "homewizard.p1.measurement.schedule.enabled", defaultValue = "true")
    boolean measurementScheduleEnabled;

    @ConfigProperty(name = "homewizard.p1.measurement.schedule.interval", defaultValue = "5s")
    String measurementScheduleInterval;

    /**
     * When api availability is toggled, we get a resolved event
     */
    @ConsumeEvent(value = "HWE-P1.resolved", blocking = true)
    void onResolved(HomewizardDeviceInfo deviceInfo) {
        log.info("P1 Meter device resolved: {}", deviceInfo);

        logBasic(deviceInfo);
        if (measurementScheduleEnabled) {
            scheduleMeasurement(deviceInfo);
        }
    }

    @ConsumeEvent(value = "HWE-P1.removed")
    void onRemoved(HomewizardDeviceInfo deviceInfo) {
        log.info("P1 Meter device removed: {}", deviceInfo);

        unscheduleMeasurement(deviceInfo);
    }

    private void logBasic(HomewizardDeviceInfo deviceInfo) {
        if (apiDisabled(deviceInfo)) {
            return;
        }

        BasicResponse response = apiProvider.basic(deviceInfo.getHost(), deviceInfo.getPort()).request();
        log.info("basic: {}", response);
    }

    public void publishMeasurement(HomewizardDeviceInfo deviceInfo) {
        if (apiDisabled(deviceInfo)) {
            return;
        }

        try {
            MeasurementResponse response = apiProvider.measurement(deviceInfo.getHost(), deviceInfo.getPort()).request();
            eventBus.publish(MEASUREMENT_EVENT, MeasurementEvent.of(deviceInfo, response));
        } catch (WebApplicationException e) {
            log.error(e.getMessage(), e);
            if (e.getResponse().getStatus() == 403) {
                unscheduleMeasurement(deviceInfo);
            }
        }
    }

    private void scheduleMeasurement(HomewizardDeviceInfo deviceInfo) {
        if (scheduler.getScheduledJob(deviceInfo.getName()) != null) {
            unscheduleMeasurement(deviceInfo);
        }
        scheduler.newJob(deviceInfo.getName())
                .setInterval(measurementScheduleInterval)
                .setTask(executionContext -> publishMeasurement(deviceInfo))
                .schedule();
    }

    private void unscheduleMeasurement(HomewizardDeviceInfo deviceInfo) {
        scheduler.unscheduleJob(deviceInfo.getName());
    }

    private boolean apiDisabled(HomewizardDeviceInfo deviceInfo) {
        if (deviceInfo.isApiEnabled()) {
            return false;
        }
        log.debug("API is not enabled");
        return true;
    }
}
