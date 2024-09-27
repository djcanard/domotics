package nl.mtbparts.domotics.homewizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class MeasurementResponse {

    @JsonProperty("wifi_ssid")
    String wifiSsid;

    @JsonProperty("wifi_strength")
    Integer wifiStrength;

    @JsonProperty("smr_version")
    Integer smrVersion;

    @JsonProperty("meter_model")
    String meterModel;

    @JsonProperty("unique_id")
    String uniqueId;

    @JsonProperty("active_tariff")
    Integer activeTariff;

    @JsonProperty("total_power_import_kwh")
    Double totalPowerImportKwh;

    @JsonProperty("total_power_import_t1_kwh")
    Double totalPowerImportT1Kwh;

    @JsonProperty("total_power_import_t2_kwh")
    Double totalPowerImportT2Kwh;

    @JsonProperty("total_power_export_kwh")
    Double totalPowerExportKwh;

    @JsonProperty("total_power_export_t1_kwh")
    Double totalPowerExportT1Kwh;

    @JsonProperty("total_power_export_t2_kwh")
    Double totalPowerExportT2Kwh;

    @JsonProperty("active_power_w")
    Integer activePowerW;

    @JsonProperty("active_power_l1_w")
    Integer activePowerL1W;

    @JsonProperty("active_current_l1_a")
    Double activeCurrentL1A;

    @JsonProperty("voltage_sag_l1_count")
    Integer voltageSagL1Count;

    @JsonProperty("voltage_swell_l1_count")
    Integer voltageSwellL1Count;

    @JsonProperty("any_power_fail_count")
    Integer anyPowerFailCount;

    @JsonProperty("long_power_fail_count")
    Integer longPowerFailCount;

    @JsonProperty("external")
    List<String> external;

}