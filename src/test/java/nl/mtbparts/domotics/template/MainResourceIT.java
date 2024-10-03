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
                                "homewizard.api.basic.timer",
                                "homewizard.api.measurement.timer",
                                "homewizard.measurement.activepowerw",
                                "homewizard.measurement.count",
                                "homewizard.measurement.totalpowerimportkwh"));
    }
}