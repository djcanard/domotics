package nl.mtbparts.domotics.homewizard.measurement;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleFunction;

import static nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent.MEASUREMENT_EVENT;

@ApplicationScoped
public class MetricsMeasurementConsumer {

    @Inject
    MeterRegistry meterRegistry;

    private final MeasurementState measurementState = new MeasurementState();
    private final Map<String, MeasurementState> activePowerValues = new ConcurrentHashMap<>();
    private final Map<String, MeasurementState> totalPowerImportValues = new ConcurrentHashMap<>();

    @ConsumeEvent(value = MEASUREMENT_EVENT)
    public void onMeasurementEvent(MeasurementEvent event) {
        String deviceId = event.getDevice().getDeviceId();

        meterRegistry.counter("homewizard.measurement.count", Tags.of("device", deviceId)).increment();

        measurementState.activePowerW = event.getMeasurement().getActivePowerW();
        measurementState.totalPowerImportKwh = event.getMeasurement().getTotalPowerImportKwh();

        updateGauge(deviceId, "homewizard.measurement.activepowerw", m -> m.activePowerW, "W", activePowerValues);
        updateGauge(deviceId, "homewizard.measurement.totalpowerimportkwh", m -> m.totalPowerImportKwh, "kWh", totalPowerImportValues);
    }

    private void updateGauge(String deviceId, String gaugeName, ToDoubleFunction<MeasurementState> supplier, String unit, Map<String, MeasurementState> values) {
        Tags tags = Tags.of("device", deviceId, "unit", unit);
        values.compute(deviceId, (k, v) -> {
            if (v == null) {
                v = meterRegistry.gauge(gaugeName, tags, measurementState, supplier);
            }
            return v;
        });
    }

    @AllArgsConstructor
    @NoArgsConstructor
    private static class MeasurementState {
        String deviceId;
        Integer activePowerW;
        Double totalPowerImportKwh;
    }
}
