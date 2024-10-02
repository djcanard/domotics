package nl.mtbparts.domotics.homewizard.measurement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import nl.mtbparts.domotics.device.Device;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MeasurementEvent {

    public static final String MEASUREMENT_EVENT = "measurement.event";

    Device device;
    MeasurementResponse measurement;

    public static MeasurementEvent of(Device device, MeasurementResponse measurement) {
        return new MeasurementEvent(device, measurement);
    }
}
