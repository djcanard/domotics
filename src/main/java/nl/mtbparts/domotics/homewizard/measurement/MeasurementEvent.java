package nl.mtbparts.domotics.homewizard.measurement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.mtbparts.domotics.device.Device;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeasurementEvent {
    private final Device device;
    private final MeasurementResponse measurement;

    public static MeasurementEvent of(Device device, MeasurementResponse measurement) {
        return new MeasurementEvent(device, measurement);
    }
}
