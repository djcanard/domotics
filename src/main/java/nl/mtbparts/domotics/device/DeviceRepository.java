package nl.mtbparts.domotics.device;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ApplicationScoped
public class DeviceRepository {

    private final Map<String, DeviceInfo> devices = new HashMap<>();

    public void add(DeviceInfo deviceInfo) {
        devices.put(deviceInfo.getDeviceId(), deviceInfo);
    }

    public void remove(DeviceInfo deviceInfo) {
        devices.remove(deviceInfo.getDeviceId());
    }

    public List<DeviceInfo> getDevices() {
        return devices.values().stream().toList();
    }

}
