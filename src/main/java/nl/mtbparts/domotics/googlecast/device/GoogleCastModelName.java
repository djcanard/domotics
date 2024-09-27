package nl.mtbparts.domotics.googlecast.device;

import java.util.List;

public class GoogleCastModelName {
    public static String CHROMECAST_HD = "Chromecast HD";
    public static String NEST_AUDIO = "Nest Audio";

    private static List<String> values = List.of(CHROMECAST_HD, NEST_AUDIO);

    public static boolean isKnown(String modelName) {
        return values.contains(modelName);
    }
}
