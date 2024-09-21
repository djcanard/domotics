package nl.mtbparts.domotics.homewizard.api;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@ApplicationScoped
public class ApiProvider {

    @CacheResult(cacheName = "basic-api")
    public BasicApi basic(String host, int port) {
        return clientBuilder(host, port).build(BasicApi.class);
    }

    @CacheResult(cacheName = "measurement-api")
    public MeasurementApi measurement(String host, int port) {
        return clientBuilder(host, port).build(MeasurementApi.class);
    }

    private RestClientBuilder clientBuilder(String host, int port) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("http://" + host + ":" + port));
    }
}
