package nl.mtbparts.domotics.googlecast.device.model;

import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import nl.mtbparts.domotics.googlecast.device.GoogleCastDevice;

@Slf4j
@ApplicationScoped
public class ChromecastHDConsumer {

    @ConsumeEvent(value = "Chromecast HD.resolved")
    void onResolved(GoogleCastDevice device) {
        log.info("Chromecast HD device resolved: {}", device);

    }

    @ConsumeEvent(value = "Chromecast HD.removed")
    void onRemoved(GoogleCastDevice device) {
        log.info("Chromecast HD device removed: {}", device);
    }
}
