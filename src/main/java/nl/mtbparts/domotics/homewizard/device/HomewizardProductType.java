package nl.mtbparts.domotics.homewizard.device;

import java.util.List;

public class HomewizardProductType {
    public static String HWE_P1 = "HWE-P1";       // P1 Meter
    public static String HWE_SKT = "HWE-SKT";     // Energy Socket
    public static String HWE_WTR = "HWE-WTR";     // Watermeter
    public static String HWE_KWH1 = "HWE-KWH1";   // kWh Meter (1 phase)
    public static String HWE_KWH3 = "HWE-KWH3";   // kWh Meter (3 phase)

    private static List<String> values = List.of(HWE_P1, HWE_SKT, HWE_WTR, HWE_KWH1, HWE_KWH3);

    public static boolean isKnown(String productType) {
        return values.contains(productType);
    }
}
