package nl.mtbparts.domotics.homewizard.measurement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.mtbparts.domotics.device.DeviceInfo;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeasurementEvent {
    private final DeviceInfo deviceInfo;
    private final MeasurementResponse measurement;

    public static MeasurementEvent of(DeviceInfo deviceInfo, MeasurementResponse measurement) {
        return new MeasurementEvent(deviceInfo, measurement);
    }
}
