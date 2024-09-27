package nl.mtbparts.domotics.homewizard.measurement;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class MeasurementConsumer {

    public static final String MEASUREMENT_EVENT = "measurement.event";

    @ConsumeEvent(value = MEASUREMENT_EVENT)
    public void onMeasurementEvent(MeasurementEvent event) {
        log.info("measurement: [{}] total: {} kWh, active: {} W",
                event.getDevice().getDeviceName(),
                event.getMeasurement().getTotalPowerImportKwh(),
                event.getMeasurement().getActivePowerW());
    }
}
