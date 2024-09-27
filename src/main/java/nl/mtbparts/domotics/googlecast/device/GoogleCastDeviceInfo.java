package nl.mtbparts.domotics.googlecast.device;

import lombok.Builder;
import lombok.Value;
import nl.mtbparts.domotics.device.DeviceInfo;

import javax.jmdns.ServiceInfo;
import java.util.Arrays;

@Builder
@Value
public class GoogleCastDeviceInfo implements DeviceInfo {
    String name;
    String host;
    int port;

    public static GoogleCastDeviceInfo of(ServiceInfo serviceInfo) {
        return GoogleCastDeviceInfo.builder()
                .name(serviceInfo.getName())
                .host(Arrays.stream(serviceInfo.getHostAddresses()).findFirst().orElse(null))
                .port(serviceInfo.getPort())
                .build();
    }

    @Override
    public String getDeviceId() {
        return name;
    }

    @Override
    public String getDeviceName() {
        return name; // md
    }

    @Override
    public String getDeviceType() {
        return "googlecast";
    }
}
