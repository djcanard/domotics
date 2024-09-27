package nl.mtbparts.domotics.device;

public interface Device {
    String getApplication();

    String getServiceName();

    String getServiceHost();

    int getServicePort();

    String getDeviceId();

    String getDeviceName();

    String getDeviceType();
}
