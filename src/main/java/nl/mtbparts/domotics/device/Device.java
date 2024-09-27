package nl.mtbparts.domotics.device;

import lombok.NonNull;

public interface Device {
    @NonNull
    String getApplication();

    @NonNull
    String getServiceName();

    @NonNull
    String getServiceHost();

    @NonNull
    int getServicePort();

    @NonNull
    String getDeviceId();

    @NonNull
    String getDeviceName();

    @NonNull
    String getDeviceType();
}
