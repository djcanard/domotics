package nl.mtbparts.domotics.template;

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
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("devices")
public class DevicesResource {

    @Inject
    DeviceRepository deviceRepository;

    @ConfigProperty(name = "grafana.endpoint", defaultValue = "")
    String grafanaEndpoint;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance devices(List<Device> devices, String grafanaEndpoint);
    }

    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return Templates.devices(deviceRepository.getDevices(), grafanaEndpoint);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices() {
        return deviceRepository.getDevices();
    }
}