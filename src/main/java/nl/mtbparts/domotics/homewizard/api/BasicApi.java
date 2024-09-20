package nl.mtbparts.domotics.homewizard.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public interface BasicApi {

    @GET
    BasicResponse basic();

}
