package nl.mtbparts.domotics.homewizard.device;

public enum HomewizardProductType {
    HWE_P1("HWE-P1"),       // P1 Meter
    HWE_SKT("HWE-SKT"),     // Energy Socket
    HWE_WTR("HWE-WTR"),     // Watermeter
    HWE_KWH1("HWE-KWH1"),   // kWh Meter (1 phase)
    HWE_KWH3("HWE-KWH3");   // kWh Meter (3 phase)

    private final String value;

    HomewizardProductType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HomewizardProductType of(String value) {
        for (HomewizardProductType s : values()) {
            if (s.value.equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown HomewizardProductType: " + value);
    }
}
