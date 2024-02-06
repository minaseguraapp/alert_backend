package co.minasegura.alert.handler.entrypoint;

import co.minasegura.alert.dto.AlertQueryFilter;
import co.minasegura.alert.dto.GetAlertResponse;
import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.service.IAlertService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.EnumMap;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

@Component
public class GetAlertLambda implements LambdaFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final EntrypointUtil util;
    private final IAlertService service;
    private final CommonsUtil commonsUtil;

    public GetAlertLambda(EntrypointUtil util, IAlertService service, CommonsUtil commonsUtil) {
        this.util = util;
        this.service = service;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public APIGatewayProxyResponseEvent handle(
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        LOGGER.info("GET Alert API started");

        if (apiGatewayProxyRequestEvent.getQueryStringParameters() == null) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        EnumMap<AlertQueryFilter, String> searchCriteria = util.getAlertFilter(
            apiGatewayProxyRequestEvent.getQueryStringParameters());

        if (!util.hasAlertRequestMinimumCriteria(searchCriteria)) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpStatusCode.BAD_REQUEST);
        }

        GetAlertResponse response = service.getAlerts(searchCriteria);

        Optional<String> jsonResponse = Optional.ofNullable(commonsUtil.toJson(response));
        LOGGER.info("GET Measurement API Finished");

        return jsonResponse
            .map(json -> new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK)
                .withBody(json))
            .orElse(new APIGatewayProxyResponseEvent().withStatusCode(
                HttpStatusCode.INTERNAL_SERVER_ERROR));
    }
}
