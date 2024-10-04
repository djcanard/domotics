package nl.mtbparts.domotics.template;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import nl.mtbparts.domotics.device.Device;
import nl.mtbparts.domotics.device.DeviceRepository;

import java.util.Comparator;
import java.util.List;

@Path("main")
public class MainResource {

    @Inject
    DeviceRepository deviceRepository;

//    @ConfigProperty(name = "grafana.endpoint")
    String grafanaEndpoint;

    @Inject
    MeterRegistry meterRegistry;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance main(List<Device> devices, List<Meter> meters, String grafanaEndpoint);
    }

    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return Templates.main(getDevices(), getMeters(), grafanaEndpoint);
    }

    @Path("/devices")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices() {
        return deviceRepository.getDevices();
    }

    @Path("/meters")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meter> getMeters() {
        return meterRegistry.getMeters().stream()
                .filter(m -> m.getId().getName().startsWith("domotics.metrics"))
                .sorted(Comparator.comparing(m -> m.getId().getName()))
                .toList();
    }
}