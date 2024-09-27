package nl.mtbparts.domotics.homewizard.device;

import lombok.Builder;
import lombok.Value;
import nl.mtbparts.domotics.device.DeviceInfo;

import javax.jmdns.ServiceInfo;
import java.util.Arrays;

@Builder
@Value
public class HomewizardDeviceInfo implements DeviceInfo {
    String name;
    String host;
    int port;
    boolean apiEnabled;
    String path;
    String serial;
    String productName;
    HomewizardProductType productType;

    public static HomewizardDeviceInfo of(ServiceInfo serviceInfo) {
        return HomewizardDeviceInfo.builder()
                .name(serviceInfo.getName())
                .host(Arrays.stream(serviceInfo.getHostAddresses()).findFirst().orElse(null))
                .port(serviceInfo.getPort())
                .apiEnabled("1".equals(serviceInfo.getPropertyString("api_enabled")))
                .path(serviceInfo.getPropertyString("path"))
                .serial(serviceInfo.getPropertyString("serial"))
                .productName(serviceInfo.getPropertyString("product_name"))
                .productType(HomewizardProductType.of(serviceInfo.getPropertyString("product_type")))
                .build();
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public String getDeviceType() {
        return productType.getValue();
    }
}
