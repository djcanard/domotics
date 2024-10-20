package nl.mtbparts.domotics.queries;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Slf4j
@Path("/events")
public class EventQueries { // TODO write tests

    @Inject
    @Channel("measurement.events")
    Multi<MeasurementEvent> measurementEvents;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType("application/json")
    public Multi<MeasurementEventView> stream() {
        return measurementEvents.map(MeasurementEventView::of);
    }

    @Builder
    @Value
    public static class MeasurementEventView {
        String deviceId;
        String deviceName;
        Integer activePowerW;

        public static MeasurementEventView of(MeasurementEvent event) {
            return MeasurementEventView.builder()
                    .deviceId(event.getDevice().getDeviceId())
                    .deviceName(event.getDevice().getDeviceName())
                    .activePowerW(event.getMeasurement().getActivePowerW())
                    .build();
        }
    }
}