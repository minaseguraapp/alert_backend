package co.minasegura.alert.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component

public class SetAlertsConfigurationFunction implements
    Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Override
    public APIGatewayProxyResponseEvent apply(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        LOGGER.info("New Request Started with body:"+apiGatewayProxyRequestEvent.getBody());
        return new APIGatewayProxyResponseEvent()
            .withStatusCode(HttpStatusCode.OK).withBody("Hello world");
    }
}