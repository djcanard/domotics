package nl.mtbparts.domotics.homewizard.device;

import lombok.Builder;
import lombok.Value;
import nl.mtbparts.domotics.device.Device;

import javax.jmdns.ServiceInfo;
import java.util.Arrays;

@Builder
@Value
public class HomewizardDevice implements Device {
    String application;
    String serviceName;
    String serviceHost;
    int servicePort;

    boolean apiEnabled;
    String path;
    String serial;
    String productName;
    String productType;

    @Override
    public String getDeviceId() {
        return serviceName;
    }

    @Override
    public String getDeviceName() {
        return productName;
    }

    @Override
    public String getDeviceType() {
        return productType;
    }

    public static HomewizardDevice of(ServiceInfo serviceInfo) {
        return HomewizardDevice.builder()
                .application(serviceInfo.getApplication())
                .serviceName(serviceInfo.getName())
                .serviceHost(Arrays.stream(serviceInfo.getHostAddresses()).findFirst().orElse(null))
                .servicePort(serviceInfo.getPort())
                .apiEnabled("1".equals(serviceInfo.getPropertyString("api_enabled")))
                .path(serviceInfo.getPropertyString("path"))
                .serial(serviceInfo.getPropertyString("serial"))
                .productName(serviceInfo.getPropertyString("product_name"))
                .productType(serviceInfo.getPropertyString("product_type"))
                .build();
    }
}
