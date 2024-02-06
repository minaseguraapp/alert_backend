package co.minasegura.alert.handler.entrypoint;

import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.model.AlertConfiguration;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class PostConfigureAlertLambda implements LambdaFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final IAlertConfigurationService alertConfigurationService;
    private final EntrypointUtil entrypointUtil;
    private final CommonsUtil commonsUtil;

    public PostConfigureAlertLambda(IAlertConfigurationService alertConfigurationService, CommonsUtil commonsUtil, EntrypointUtil entrypointUtil) {
        this.alertConfigurationService = alertConfigurationService;
        this.commonsUtil = commonsUtil;
        this.entrypointUtil= entrypointUtil;
    }
    @Override
    public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        LOGGER.info("POST Configure Alert API started");

        if (apiGatewayProxyRequestEvent.getBody() == null) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        final AlertConfiguration alertToRegister =
                commonsUtil.toObject(apiGatewayProxyRequestEvent.getBody(), AlertConfiguration.class);

        if(!entrypointUtil.isAlertConfigurationValid(alertToRegister)){
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        boolean successRegister = alertConfigurationService.configureAlert(alertToRegister);
        LOGGER.info("POST Configure Alert API Finished");

        return new APIGatewayProxyResponseEvent().withStatusCode(
                successRegister ? HttpStatusCode.CREATED : HttpStatusCode.INTERNAL_SERVER_ERROR);

    }
}
