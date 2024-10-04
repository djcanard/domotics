package nl.mtbparts.domotics.template;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.device.DeviceRepository;
import nl.mtbparts.domotics.homewizard.device.HomewizardDevice;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestHTTPEndpoint(MainResource.class)
@QuarkusTest
class MainResourceTest {

    @Inject
    DeviceRepository deviceRepository;

    @Test
    void shouldGetDevice() {
        deviceRepository.add(HomewizardDevice.builder()
                .productType("HWE-P1")
                .build());

        String response = given().contentType(ContentType.JSON)
                .when().get("/devices")
                .then().statusCode(200).extract().asString();
        assertThat(response).contains("HWE-P1");
    }

    @Test
    void shouldGetMeter() {
        String response = given().contentType(ContentType.JSON)
                .when().get("/meters")
                .then().statusCode(200).extract().asString();
        assertThat(response).contains("domotics.metrics.homewizard.measurement.count");
    }
}