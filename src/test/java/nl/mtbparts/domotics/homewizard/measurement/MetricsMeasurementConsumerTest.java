package nl.mtbparts.domotics.homewizard.measurement;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.RequiredSearch;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.device.Device;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class MetricsMeasurementConsumerTest {

    @Inject
    MeterRegistry meterRegistry;

    @Inject
    MetricsMeasurementConsumer metricsMeasurementConsumer;

    @Test
    void shouldHaveCounter() {
        MeasurementResponse response = MeasurementResponse.builder()
                .activePowerW(200)
                .totalPowerImportKwh(1000d)
                .build();

        MeasurementEvent event = MeasurementEvent.of(new MockDevice("counterId"), response);

        metricsMeasurementConsumer.onMeasurementEvent(event);

        RequiredSearch search = meterRegistry.get("homewizard.measurement.count").tags("device", "counterId");
        assertThat(search.counters()).hasSize(1);
        assertThat(search.counter().count()).isEqualTo(1);
    }

    @Test
    void shouldHaveActivePowerGauge() {
        MeasurementResponse response1 = MeasurementResponse.builder()
                .activePowerW(200)
                .build();
        MeasurementEvent event1 = MeasurementEvent.of(new MockDevice("activePowerId"), response1);

        MeasurementResponse response2 = MeasurementResponse.builder()
                .activePowerW(300)
                .build();
        MeasurementEvent event2 = MeasurementEvent.of(new MockDevice("activePowerId"), response2);

        metricsMeasurementConsumer.onMeasurementEvent(event1);
        metricsMeasurementConsumer.onMeasurementEvent(event2);

        RequiredSearch search = meterRegistry.get("homewizard.measurement.activepowerw").tags("device", "activePowerId", "unit", "W");
        assertThat(search.gauges()).hasSize(1);
        assertThat(search.gauge().value()).isEqualTo(300);
    }

    @Test
    void shouldHaveTotalPowerImportGauge() {
        MeasurementResponse response1 = MeasurementResponse.builder()
                .activePowerW(0)
                .totalPowerImportKwh(1000d)
                .build();
        MeasurementEvent event1 = MeasurementEvent.of(new MockDevice("totalPowerImportId"), response1);

        MeasurementResponse response2 = MeasurementResponse.builder()
                .activePowerW(0)
                .totalPowerImportKwh(2000d)
                .build();
        MeasurementEvent event2 = MeasurementEvent.of(new MockDevice("totalPowerImportId"), response2);

        metricsMeasurementConsumer.onMeasurementEvent(event1);
        metricsMeasurementConsumer.onMeasurementEvent(event2);

        RequiredSearch search = meterRegistry.get("homewizard.measurement.totalpowerimportkwh").tags("device", "totalPowerImportId", "unit", "kWh");
        assertThat(search.gauges()).hasSize(1);
        assertThat(search.gauge().value()).isEqualTo(2000d);
    }

    private static class MockDevice implements Device {
        public MockDevice(String deviceId) {
            this.deviceId = deviceId;
        }

        private final String deviceId;

        @Override
        public String getApplication() {
            return "app";
        }

        @Override
        public String getServiceName() {
            return "service-name";
        }

        @Override
        public String getServiceHost() {
            return "service-host";
        }

        @Override
        public int getServicePort() {
            return 42;
        }

        @Override
        public String getDeviceId() {
            return deviceId;
        }

        @Override
        public String getDeviceName() {
            return "device-name";
        }

        @Override
        public String getDeviceType() {
            return "device-type";
        }
    }
}