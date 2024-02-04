package co.minasegura.alert.handler.entrypoint;

import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.service.IAlertConfigurationService;
import co.minasegura.alert.util.CommonsUtil;
import co.minasegura.alert.util.EntrypointUtil;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetAlertLambda implements LambdaFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    public GetAlertLambda() {
    }

    @Override
    public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        return null;
    }
}
