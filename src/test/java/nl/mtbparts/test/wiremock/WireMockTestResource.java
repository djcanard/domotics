package nl.mtbparts.test.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.junit.jupiter.api.AfterEach;

import java.util.Map;

public class WireMockTestResource implements QuarkusTestResourceLifecycleManager {

    public static WireMockServer wireMockServer;

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        return Map.of();
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}