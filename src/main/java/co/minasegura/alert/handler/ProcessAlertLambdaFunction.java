package co.minasegura.alert.handler;

import co.minasegura.alert.events.MeasurementEvent;
import co.minasegura.alert.model.alert.Alert;
import co.minasegura.alert.model.measurement.Measurement;
import co.minasegura.alert.properties.AlertProperties;
import co.minasegura.alert.service.IAlertService;
import co.minasegura.alert.util.CommonsUtil;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import java.util.Optional;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ProcessAlertLambdaFunction implements Function<Message<SQSEvent>, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final CommonsUtil commonsUtil;
    private final IAlertService alertService;
    private final AlertProperties alertProperties;

    public ProcessAlertLambdaFunction(CommonsUtil commonsUtil, IAlertService alertService,
        AlertProperties alertProperties) {
        this.commonsUtil = commonsUtil;
        this.alertService = alertService;
        this.alertProperties = alertProperties;
    }

    @Override
    public Void apply(Message<SQSEvent> message) {
        final SQSEvent msg = message.getPayload();

        LOGGER.info("The SQS measurement received: [{}]", msg);

        for (var record : msg.getRecords()) {

            LOGGER.info("Processing : [{}]", record.toString());

            final Optional<MeasurementEvent> receivedMeasurementEvent = Optional.ofNullable(
                commonsUtil.toObject(record.getBody(), MeasurementEvent.class));

            if (receivedMeasurementEvent.isEmpty()) {
                LOGGER.info("The measurement could not be created [{}]", record.getBody());
                continue;
            }

            if (!alertProperties.getEventName().equals(receivedMeasurementEvent.get().eventType())) {
                LOGGER.info("The event type is not valid [{}]",
                    receivedMeasurementEvent.get().eventType());
                continue;
            }

            final Measurement measurementToProcess = receivedMeasurementEvent.get().measurement();

            final Optional<Alert> newAlert = Optional.ofNullable(
                alertService.processMeasurementAlert(measurementToProcess));

            if (newAlert.isPresent()) {
                LOGGER.info("The new alert was created: [{}]", commonsUtil.toJson(newAlert));
            } else {
                LOGGER.info("The measurement doesn't generate alert [{}]",
                    commonsUtil.toJson(measurementToProcess));
            }
        }
        return null;
    }
}