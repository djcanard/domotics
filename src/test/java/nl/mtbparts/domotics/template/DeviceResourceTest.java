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

@TestHTTPEndpoint(DevicesResource.class)
@QuarkusTest
class DeviceResourceTest {

    @Inject
    DeviceRepository deviceRepository;

    @Test
    void shouldDiscoverP1Meter() {
        deviceRepository.add(HomewizardDevice.builder()
                .productType("HWE-P1")
                .build());

        String response = given().contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200).extract().asString();
        assertThat(response).contains("HWE-P1");
    }
}