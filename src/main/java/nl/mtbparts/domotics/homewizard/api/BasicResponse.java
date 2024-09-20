package nl.mtbparts.domotics.homewizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BasicResponse {

    @JsonProperty("product_name")
    String productName;

    @JsonProperty("product_type")
    String productType;

    @JsonProperty("serial")
    String serial;

    @JsonProperty("firmware_version")
    String firmwareVersion;

    @JsonProperty("api_version")
    String apiVersion;
}