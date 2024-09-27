package nl.mtbparts.domotics.googlecast.device.model;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.googlecast.device.GoogleCastDevice;

@Slf4j
@ApplicationScoped
public class NestAudioConsumer {

    @ConsumeEvent(value = "Nest Audio.resolved")
    void onResolved(GoogleCastDevice device) {
        log.info("Nest Audio device resolved: {}", device);

    }

    @ConsumeEvent(value = "Nest Audio.removed")
    void onRemoved(GoogleCastDevice device) {
        log.info("Nest Audio device removed: {}", device);
    }
}
