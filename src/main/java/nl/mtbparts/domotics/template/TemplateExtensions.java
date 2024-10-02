package nl.mtbparts.domotics.template;

import io.quarkus.qute.TemplateExtension;

@TemplateExtension
public class TemplateExtensions {

    public static String format(Number value, int decimals) {
        return String.format("%." + decimals + "f", value);
    }
}