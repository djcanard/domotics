package nl.mtbparts.domotics.homewizard.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v1/data")
public interface MeasurementApi {

    @GET
    MeasurementResponse request();

}
