package nl.mtbparts.test.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.junit.jupiter.api.AfterEach;

import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockTestResource implements QuarkusTestResourceLifecycleManager {

    WireMockServer wireMockServer;

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(options()
                .dynamicPort()
                .notifier(new Slf4jNotifier(true)));

        wireMockServer.start();

        return Map.of();
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(wireMockServer, new TestInjector.AnnotatedAndMatchesType(InjectWireMock.class, WireMockServer.class));
    }
}