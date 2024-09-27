package nl.mtbparts.domotics.googlecast.device.model;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.googlecast.device.GoogleCastDevice;

@Slf4j
@ApplicationScoped
public class GenericGoogleCastConsumer {

    @ConsumeEvent(value = "Generic Google Cast.resolved")
    void onResolved(GoogleCastDevice device) {
        log.info("Generic Google Cast device resolved: {}", device);

    }

    @ConsumeEvent(value = "Generic Google Cast.removed")
    void onRemoved(GoogleCastDevice device) {
        log.info("Generic Google Cast device removed: {}", device);
    }
}
