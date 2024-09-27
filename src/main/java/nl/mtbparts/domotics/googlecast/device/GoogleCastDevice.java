package nl.mtbparts.domotics.googlecast.device;

import lombok.Builder;
import lombok.Value;
import nl.mtbparts.domotics.device.Device;

import javax.jmdns.ServiceInfo;
import java.util.Arrays;

@Builder
@Value
public class GoogleCastDevice implements Device {
    String application;
    String serviceName;
    String serviceHost;
    int servicePort;

    String id; // id
    Integer capabilities; // ca
    String friendlyName; // fn
    String modelName; // md
    Integer protocolVersion; // ve
    String iconPath; // ic
    String appTitle; // rs

    // cd, nf, rs, ca, ct, rm, bs, st

    @Override
    public String getDeviceId() {
        return id;
    }

    @Override
    public String getDeviceName() {
        return friendlyName;
    }

    @Override
    public String getDeviceType() {
        return modelName;
    }

    public static GoogleCastDevice of(ServiceInfo serviceInfo) {
        return GoogleCastDevice.builder()
                .application(serviceInfo.getApplication())
                .serviceName(serviceInfo.getName())
                .serviceHost(Arrays.stream(serviceInfo.getHostAddresses()).findFirst().orElse(null))
                .servicePort(serviceInfo.getPort())
                .id(serviceInfo.getPropertyString("id"))
                .capabilities(getIntegerValue(serviceInfo.getPropertyString("ca"), -1))
                .friendlyName(serviceInfo.getPropertyString("fn"))
                .modelName(serviceInfo.getPropertyString("md"))
                .protocolVersion(getIntegerValue(serviceInfo.getPropertyString("ve"), -1))
                .iconPath(serviceInfo.getPropertyString("ic"))
                .appTitle(serviceInfo.getPropertyString("rs"))
                .build();
    }

    public static Integer getIntegerValue(String value, int defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }
}
