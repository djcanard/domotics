package nl.mtbparts.domotics.template;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;

/**
 * These tests will only succeed if you have a HomeWizard P1 meter in your local network
 */
@TestHTTPEndpoint(MainResource.class)
@QuarkusIntegrationTest
class MainResourceIT {

    @Test
    void shouldDiscoverP1MeterDevice() {
        with()
                .pollInterval(Duration.ofSeconds(1))
                .await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(given().contentType(ContentType.JSON)
                        .when().get("/devices")
                        .then().statusCode(200).extract().asString())
                        .contains("HWE-P1"));
    }

    @Test
    void shouldGetMeters() {
        with()
                .pollInterval(Duration.ofSeconds(1))
                .await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(given().contentType(ContentType.JSON)
                        .when().get("/meters")
                        .then().statusCode(200).extract().jsonPath()
                        .getList("id.name")).contains(
                                "domotics.metrics.homewizard.api.basic.timer",
                                "domotics.metrics.homewizard.api.measurement.timer",
                                "domotics.metrics.homewizard.measurement.activepowerw",
                                "domotics.metrics.homewizard.measurement.count",
                                "domotics.metrics.homewizard.measurement.totalpowerimportkwh"));
    }
}