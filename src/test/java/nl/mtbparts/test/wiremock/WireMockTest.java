package nl.mtbparts.test.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResource;

@QuarkusTestResource(WireMockTestResource.class)
public abstract class WireMockTest {

    public WireMockServer wireMock() {
        return WireMockTestResource.wireMockServer;
    }
}
