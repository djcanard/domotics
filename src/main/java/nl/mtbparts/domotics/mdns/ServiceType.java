package nl.mtbparts.domotics.mdns;

public enum ServiceType {
    HWENERGY("_hwenergy._tcp.local."),
    GOOGLECAST("_googlecast._tcp.local.");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ServiceType of(String value) {
        for (ServiceType s : values()) {
            if (s.value.equals(value)) {
                return s;
            }
        }
        return null;
    }
}
