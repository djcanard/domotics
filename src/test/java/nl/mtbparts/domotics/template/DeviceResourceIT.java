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

@TestHTTPEndpoint(DevicesResource.class)
@QuarkusIntegrationTest
class DeviceResourceIT {

    @Test
    void shouldDiscoverP1Meter() {
        with()
                .pollInterval(Duration.ofSeconds(1))
                .await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(given().contentType(ContentType.JSON)
                        .when().get()
                        .then().statusCode(200).extract().asString())
                        .contains("HWE-P1"));
    }
}