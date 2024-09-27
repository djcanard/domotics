package nl.mtbparts.domotics.template;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import nl.mtbparts.domotics.device.DeviceInfo;
import nl.mtbparts.domotics.device.DeviceRepository;

import java.util.List;

@Path("devices")
public class DevicesResource {

    @Inject
    DeviceRepository deviceRepository;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance devices(List<DeviceInfo> devices);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return Templates.devices(deviceRepository.getDevices());
    }
}