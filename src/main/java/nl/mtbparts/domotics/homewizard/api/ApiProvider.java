package nl.mtbparts.domotics.homewizard.api;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;

@ApplicationScoped
public class ApiProvider {

    @ConfigProperty(name = "homewizard.device.host")
    private String host;

    @Produces
    BasicApi basicApi() {
        return clientBuilder().build(BasicApi.class);
    }

    @Produces
    MeasurementApi measurementApi() {
        return clientBuilder().build(MeasurementApi.class);
    }

    private QuarkusRestClientBuilder clientBuilder() {
        return QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create("http://" + host));
    }
}
