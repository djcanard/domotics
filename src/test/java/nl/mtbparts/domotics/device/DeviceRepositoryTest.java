package nl.mtbparts.domotics.device;

import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusComponentTest
class DeviceRepositoryTest {

    @Inject
    DeviceRepository deviceRepository;

    @Test
    void shouldAddGetAndRemove() {
        assertThat(deviceRepository.deviceCount()).isZero();

        Device device1 = new MockDevice("id1");
        deviceRepository.add(device1);

        assertThat(deviceRepository.deviceCount()).isEqualTo(1);

        Device device2 = new MockDevice("id2");
        deviceRepository.add(device2);
        deviceRepository.add(device2);
        deviceRepository.add(device2);

        assertThat(deviceRepository.deviceCount()).isEqualTo(2);

        Device actual1 = deviceRepository.findById("id1");
        assertThat(actual1).isEqualTo(device1);

        Device actual2 = deviceRepository.getById("id2");
        assertThat(actual2).isEqualTo(device2);

        deviceRepository.remove("id1");

        assertThat(deviceRepository.deviceCount()).isEqualTo(1);
    }

    @Test
    void shouldRemoveAll() {
        assertThat(deviceRepository.deviceCount()).isZero();

        deviceRepository.add(new MockDevice("id1"));
        deviceRepository.add(new MockDevice("id2"));
        deviceRepository.add(new MockDevice("id3"));

        assertThat(deviceRepository.deviceCount()).isEqualTo(3);

        deviceRepository.removeAll();

        assertThat(deviceRepository.deviceCount()).isZero();
    }

    @AllArgsConstructor
    static class MockDevice implements Device {

        private String id;

        @Override
        public String getApplication() {
            return "app";
        }

        @Override
        public String getServiceName() {
            return "serviceName";
        }

        @Override
        public String getServiceHost() {
            return "serviceHost";
        }

        @Override
        public int getServicePort() {
            return 999;
        }

        @Override
        public String getDeviceId() {
            return id;
        }

        @Override
        public String getDeviceName() {
            return "deviceName";
        }

        @Override
        public String getDeviceType() {
            return "deviceType";
        }
    }
}