package nl.mtbparts.domotics.test;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class HomewizardTestProfile implements QuarkusTestProfile {

    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "service-discovery.service-types[0]", "my.service.1",
                "service-discovery.service-types[1]", "my.service.2");
    }

}