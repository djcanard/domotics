package nl.mtbparts.domotics.homewizard.measurement;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent.MEASUREMENT_EVENT;

@Slf4j
@ApplicationScoped
public class LogMeasurementConsumer {

    @ConsumeEvent(value = MEASUREMENT_EVENT)
    public void onMeasurementEvent(MeasurementEvent event) {
        log.info("[{}] total: {} kWh, active: {} W",
                event.getDevice().getDeviceId(),
                event.getMeasurement().getTotalPowerImportKwh(),
                event.getMeasurement().getActivePowerW());
    }
}
