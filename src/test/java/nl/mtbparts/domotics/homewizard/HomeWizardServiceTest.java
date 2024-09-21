package nl.mtbparts.domotics.homewizard;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;
import nl.mtbparts.test.wiremock.WireMockTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class HomeWizardServiceTest extends WireMockTest {

    @Inject
    HomeWizardService homeWizardService;

    @Test
    void testBasic() {
        wireMock().stubFor(get(urlEqualTo("/api"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("p1meter-basic.json")));

        BasicResponse response = homeWizardService.basic();

        assertThat(response)
                .isEqualTo(BasicResponse.builder()
                        .productName("P1 Meter")
                        .productType("HWE-P1")
                        .firmwareVersion("5.18")
                        .serial("5c2faf0ad04a")
                        .apiVersion("v1")
                        .build());
    }

    @Disabled("fix assertion")
    @Test
    void testMeasurement() {
        wireMock().stubFor(get(urlEqualTo("/api/v1/api"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("p1meter-measurement.json")));

        MeasurementResponse response = homeWizardService.measurement();

        assertThat(response)
                .isEqualTo(MeasurementResponse.builder()
                        .smrVersion(41)
                        .build());
    }
}