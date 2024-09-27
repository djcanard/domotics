package nl.mtbparts.domotics.device;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class DeviceRepository {

    private final Map<String, Device> devices = new HashMap<>();

    public void add(Device device) {
        devices.put(device.getDeviceId(), device);
    }

    public void remove(Device device) {
        devices.remove(device.getDeviceId());
    }

    public List<Device> getDevices() {
        return devices.values().stream().toList();
    }

}
