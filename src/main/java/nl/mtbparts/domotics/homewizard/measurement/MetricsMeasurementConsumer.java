package nl.mtbparts.domotics.homewizard.measurement;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent.MEASUREMENT_EVENT;

@ApplicationScoped
public class MetricsMeasurementConsumer {

    @Inject
    MeterRegistry meterRegistry;

    /**
     * Map with gauge names with values of measurements per device
     */
    private final Map<String, Map<String, GaugeValue>> gaugeValues = new ConcurrentHashMap<>();

    @ConsumeEvent(value = MEASUREMENT_EVENT)
    public void onMeasurementEvent(MeasurementEvent event) {
        String deviceId = event.getDevice().getDeviceId();

        meterRegistry.counter("homewizard.measurement.count", Tags.of("device", deviceId)).increment();

        updateGauge("homewizard.measurement.activepowerw", "W", event.getDevice().getDeviceId(), event.getMeasurement().getActivePowerW().doubleValue());
        updateGauge("homewizard.measurement.totalpowerimportkwh", "kWh", event.getDevice().getDeviceId(), event.getMeasurement().getTotalPowerImportKwh());
    }

    private void updateGauge(String gaugeName, String unit, String deviceId, Double value) {
        gaugeValues.compute(gaugeName, (k, v) -> {
           if (v == null) {
               v = new ConcurrentHashMap<>();
           }
           return v;
        });

        gaugeValues.get(gaugeName).compute(deviceId, (k, v) -> {
            if (v == null) {
                GaugeValue gaugeValue = GaugeValue.of(value);
                v = meterRegistry.gauge(gaugeName, Tags.of("device", deviceId, "unit", unit), gaugeValue, GaugeValue::get);
            } else {
                v.update(value);
            }
            return v;
        });
    }

    private static class GaugeValue {
        private Double value;

        private GaugeValue(Double value) {
            this.value = value;
        }

        public void update(Double value) {
            this.value = value;
        }

        public Double get() {
            return value;
        }

        public static GaugeValue of(Double value) {
            return new GaugeValue(value);
        }
    }
}
