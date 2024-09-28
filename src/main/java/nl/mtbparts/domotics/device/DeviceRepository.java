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

    public void remove(String id) {
        devices.remove(id);
    }

    public void removeAll() {
        devices.clear();
    }

    public Device findById(String id) {
        return devices.get(id);
    }

    public Device getById(String id) {
        if (!devices.containsKey(id)) {
            throw new DeviceNotFoundException(id);
        }
        return devices.get(id);
    }

    public int deviceCount() {
        return devices.size();
    }

    public List<Device> getDevices() {
        return devices.values().stream().toList();
    }

    public static class DeviceNotFoundException extends RuntimeException {
        public DeviceNotFoundException(String id) {
            super("device-not-found: " + id);
        }
    }

}
