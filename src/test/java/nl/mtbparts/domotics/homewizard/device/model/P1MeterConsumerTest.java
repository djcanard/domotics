package nl.mtbparts.domotics.homewizard.device.model;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.quarkus.scheduler.Scheduler;
import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import nl.mtbparts.domotics.homewizard.api.ApiProvider;
import nl.mtbparts.domotics.homewizard.api.BasicApi;
import nl.mtbparts.domotics.homewizard.api.MeasurementApi;
import nl.mtbparts.domotics.homewizard.api.MeasurementResponse;
import nl.mtbparts.domotics.homewizard.device.HomewizardDevice;
import nl.mtbparts.domotics.homewizard.measurement.MeasurementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusComponentTest
class P1MeterConsumerTest {

    @Inject
    P1MeterConsumer p1MeterConsumer;

    @InjectMock
    ApiProvider apiProvider;

    @InjectMock
    Scheduler scheduler;

    @InjectMock
    EventBus eventBus;

    @InjectMock
    MeterRegistry meterRegistry;

    MeterRegistry.Config config = mock(MeterRegistry.Config.class);
    Clock mockClock = mock(Clock.class);
    Timer mockTimer = mock(Timer.class);

    @BeforeEach
    void beforeEach() {
        when(config.clock()).thenReturn(mockClock);
        when(meterRegistry.config()).thenReturn(config);
        when(meterRegistry.timer(anyString(), any(String[].class))).thenReturn(mockTimer);
    }

    @Test
    void shouldScheduleMeasurementOnResolved() {
        HomewizardDevice device = HomewizardDevice.builder()
                .serviceHost("host")
                .servicePort(888)
                .serviceName("service-name")
                .apiEnabled(true)
                .build();

        when(apiProvider.basic(any(), anyInt())).thenReturn(mock(BasicApi.class));

        Scheduler.JobDefinition jobDefinitionMock = mock(Scheduler.JobDefinition.class);
        when(jobDefinitionMock.setInterval(any())).thenReturn(jobDefinitionMock);
        when(jobDefinitionMock.setTask(any(Consumer.class))).thenReturn(jobDefinitionMock);
        when(scheduler.newJob(any())).thenReturn(jobDefinitionMock);

        p1MeterConsumer.onResolved(device);

        verify(apiProvider).basic("host", 888);
        verify(scheduler).getScheduledJob("service-name");
        verify(scheduler).newJob("service-name");
    }

    @Test
    void shouldUnscheduleMeasurementOnRemoved() {
        HomewizardDevice device = HomewizardDevice.builder()
                .serviceName("service-name")
                .build();

        p1MeterConsumer.onRemoved(device);

        verify(scheduler).unscheduleJob("service-name");
    }

    @Test
    void shouldPublishMeasurement() {
        HomewizardDevice device = HomewizardDevice.builder()
                .serviceHost("host")
                .servicePort(888)
                .serviceName("service-name")
                .apiEnabled(true)
                .build();

        MeasurementApi apiMock = mock(MeasurementApi.class);
        MeasurementResponse response = MeasurementResponse.builder().build();
        when(apiMock.request()).thenReturn(response);
        when(apiProvider.measurement(any(), anyInt())).thenReturn(apiMock);

        p1MeterConsumer.publishMeasurement(device);

        verify(eventBus).publish("measurement.event", MeasurementEvent.of(device, response));
    }
}