package co.minasegura.alert.handler.entrypoint;

import co.minasegura.alert.dto.AlertConfigurationFilter;
import co.minasegura.alert.dto.GetAlertConfigurationResponse;
import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.EnumMap;
import java.util.Optional;

@Component
public class GetAlertConfigurationLambda implements LambdaFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final IAlertConfigurationService alertConfigurationService;
    private final EntrypointUtil entrypointUtil;
    private final CommonsUtil commonsUtil;

    public GetAlertConfigurationLambda(IAlertConfigurationService alertConfigurationService, EntrypointUtil entrypointUtil, CommonsUtil commonsUtil) {
        this.alertConfigurationService = alertConfigurationService;
        this.entrypointUtil = entrypointUtil;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        LOGGER.info("GET Alert Configuration API started");

        if (apiGatewayProxyRequestEvent.getQueryStringParameters() == null) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        EnumMap<AlertConfigurationFilter, String> searchCriteria = entrypointUtil.getAlertFilter(
                apiGatewayProxyRequestEvent.getQueryStringParameters());

        if (!entrypointUtil.hasRequestMinimumCriteria(searchCriteria)) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        GetAlertConfigurationResponse response = alertConfigurationService.getAlertConfiguration(searchCriteria);

        Optional<String> jsonResponse = Optional.ofNullable(commonsUtil.toJson(response));
        LOGGER.info("GET Alert Configuration API Finished");

        return jsonResponse
                .map(json -> new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK)
                        .withBody(json))
                .orElse(new APIGatewayProxyResponseEvent().withStatusCode(
                        HttpStatusCode.INTERNAL_SERVER_ERROR));
    }
}
