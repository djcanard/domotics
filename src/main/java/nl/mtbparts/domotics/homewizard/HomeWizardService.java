package nl.mtbparts.domotics.homewizard;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.homewizard.api.BasicApi;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.api.MeasurementApi;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;

@ApplicationScoped
public class HomeWizardService {

    @Inject
    BasicApi basicApi;

    @Inject
    MeasurementApi measurementApi;

    public BasicResponse basic() {
        return basicApi.basic();
    }

    public MeasurementResponse measurement() {
        return measurementApi.measurement();
    }
}
