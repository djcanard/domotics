package nl.mtbparts.domotics.queries;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Path("/meters")
public class MeterQueries { // TODO create test

    @Inject
    MeterRegistry meterRegistry;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MeterView> getMeters() {
        return meterRegistry.getMeters().stream()
                .filter(m -> m.getId().getName().startsWith("domotics.metrics"))
                .sorted(Comparator.comparing(m -> m.getId().getName()))
                .map(MeterQueries::mapToView)
                .toList();
    }

    static MeterView mapToView(Meter meter) {
        return MeterView.builder()
                .name(meter.getId().getName())
                .device(meter.getId().getTag("device"))
                .unit(meter.getId().getTag("unit"))
                .type(meter.getId().getType().name())
                .measures(mapToView(meter.measure()))
                .build();
    }

    static List<MeasureView> mapToView(Iterable<Measurement> measurements) {
        List<MeasureView> measureViews = new ArrayList<>();
        measurements.forEach(m -> {
            measureViews.add(MeasureView.builder()
                    .value(m.getValue())
                    .name(m.getStatistic().name())
                    .tagValueRepresentation(m.getStatistic().getTagValueRepresentation())
                    .build());
        });
        return measureViews;
    }

    @Value
    @Builder
    public static class MeterView {
        String name;
        String device;
        String unit;
        String type;
        List<MeasureView> measures;
    }

    @Value
    @Builder
    public static class MeasureView {
        Double value;
        String name;
        String tagValueRepresentation;
    }
}
