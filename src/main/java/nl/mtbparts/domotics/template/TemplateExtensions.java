package nl.mtbparts.domotics.template;

import io.quarkus.qute.TemplateExtension;
import nl.mtbparts.domotics.homewizard.api.BasicResponse;
import nl.mtbparts.domotics.homewizard.device.HomewizardProductType;

@TemplateExtension
public class TemplateExtensions {

    public static HomewizardProductType productTypeEnum(BasicResponse basicResponse) {
        return HomewizardProductType.of(basicResponse.getProductType());
    }
}