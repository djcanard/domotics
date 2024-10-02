package nl.mtbparts.domotics.homewizard.device.model;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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
import nl.mtbparts.domotics.homewizard.device.HomewizardDevice;
import nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent.MEASUREMENT_EVENT;

@Slf4j
@ApplicationScoped
public class P1MeterConsumer {

    @Inject
    ApiProvider apiProvider;

    @Inject
    Scheduler scheduler;

    @Inject
    EventBus eventBus;

    @Inject
    MeterRegistry meterRegistry;

    @ConfigProperty(name = "homewizard.p1.measurement.schedule.enabled", defaultValue = "true")
    boolean measurementScheduleEnabled;

    @ConfigProperty(name = "homewizard.p1.measurement.schedule.interval", defaultValue = "5s")
    String measurementScheduleInterval;

    /**
     * When api availability is toggled, we get a resolved event
     */
    @ConsumeEvent(value = "HWE-P1.resolved", blocking = true)
    void onResolved(HomewizardDevice device) {
        log.info("P1 Meter device resolved: {}", device);

        logBasic(device);
        if (measurementScheduleEnabled) {
            scheduleMeasurement(device);
        }
    }

    @ConsumeEvent(value = "HWE-P1.removed")
    void onRemoved(HomewizardDevice device) {
        log.info("P1 Meter device removed: {}", device);

        unscheduleMeasurement(device);
    }

    private void logBasic(HomewizardDevice device) {
        if (apiDisabled(device)) {
            return;
        }

        Timer.Sample sample = Timer.start(meterRegistry);
        BasicResponse response = apiProvider.basic(device.getServiceHost(), device.getServicePort()).request();
        sample.stop(meterRegistry.timer("homewizard.api.basic.timer", "device", device.getDeviceId()));

        log.info("basic: {}", response);
    }

    public void publishMeasurement(HomewizardDevice device) {
        if (apiDisabled(device)) {
            return;
        }

        try {
            Timer.Sample sample = Timer.start(meterRegistry);
            MeasurementResponse response = apiProvider.measurement(device.getServiceHost(), device.getServicePort()).request();
            sample.stop(meterRegistry.timer("homewizard.api.measurement.timer", "device", device.getDeviceId()));

            eventBus.publish(MEASUREMENT_EVENT, MeasurementEvent.of(device, response));
        } catch (WebApplicationException e) {
            log.error(e.getMessage(), e);
            if (e.getResponse().getStatus() == 403) {
                unscheduleMeasurement(device);
            }
        }
    }

    private void scheduleMeasurement(HomewizardDevice device) {
        if (scheduler.getScheduledJob(device.getServiceName()) != null) {
            unscheduleMeasurement(device);
        }
        scheduler.newJob(device.getServiceName())
                .setInterval(measurementScheduleInterval)
                .setTask(executionContext -> publishMeasurement(device))
                .schedule();
    }

    private void unscheduleMeasurement(HomewizardDevice device) {
        scheduler.unscheduleJob(device.getServiceName());
    }

    private boolean apiDisabled(HomewizardDevice device) {
        if (device.isApiEnabled()) {
            return false;
        }
        log.debug("API is not enabled");
        return true;
    }
}
