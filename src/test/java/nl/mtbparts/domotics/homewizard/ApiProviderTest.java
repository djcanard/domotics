package nl.mtbparts.domotics.homewizard;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.homewizard.api.ApiProvider;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;
import nl.mtbparts.test.wiremock.WireMockTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class ApiProviderTest extends WireMockTest {

    @Inject
    ApiProvider apiProvider;

    @Test
    void testBasic() {
        wireMock().stubFor(get(urlEqualTo("/api"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("p1meter-basic.json")));

        BasicResponse response = apiProvider
                .basic(wireMock().getOptions().bindAddress(), wireMock().port())
                .request();

        assertThat(response)
                .isEqualTo(BasicResponse.builder()
                        .productName("P1 Meter")
                        .productType("HWE-P1")
                        .firmwareVersion("5.18")
                        .serial("5c2faf0ad04a")
                        .apiVersion("v1")
                        .build());
    }

    @Test
    void testMeasurement() {
        wireMock().stubFor(get(urlEqualTo("/api/v1/data"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("p1meter-measurement.json")));

        MeasurementResponse response = apiProvider
                .measurement(wireMock().getOptions().bindAddress(), wireMock().port())
                .request();

        assertThat(response)
                .isEqualTo(MeasurementResponse.builder()
                        .wifiSsid("net")
                        .wifiStrength(100)
                        .smrVersion(42)
                        .meterModel("Kaifa AIFA-METER")
                        .uniqueId("4530303235303030303137303532323134")
                        .activeTariff(2)
                        .totalPowerImportKwh(Double.valueOf("41866.752"))
                        .totalPowerImportT1Kwh(Double.valueOf("20927.603"))
                        .totalPowerImportT2Kwh(Double.valueOf("20939.149"))
                        .totalPowerExportKwh(Double.valueOf("44.44"))
                        .totalPowerExportT1Kwh(Double.valueOf("55.55"))
                        .totalPowerExportT2Kwh(Double.valueOf("66.66"))
                        .activePowerW(436)
                        .activePowerL1W(457)
                        .activeCurrentL1A(Double.valueOf("2"))
                        .voltageSagL1Count(88)
                        .voltageSwellL1Count(99)
                        .anyPowerFailCount(5)
                        .longPowerFailCount(3)
                        .external(List.of())
                        .build());
    }
}