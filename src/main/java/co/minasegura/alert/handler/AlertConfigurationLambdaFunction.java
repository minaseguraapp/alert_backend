package co.minasegura.alert.handler;

import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.handler.route.Route;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.http.HttpStatusCode;

import java.util.Map;
import java.util.function.Function;

@Component
public class AlertConfigurationLambdaFunction implements
        Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Map<Route, LambdaFunction> router;

    public AlertConfigurationLambdaFunction(Map<Route, LambdaFunction> router) {
        this.router = router;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(
            APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        Route route = buildRouter(apiGatewayProxyRequestEvent);
        LambdaFunction lambdaToExecute = this.router.get(route);

        if (lambdaToExecute == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(
                    HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        return lambdaToExecute.handle(apiGatewayProxyRequestEvent);
    }

    private Route buildRouter(
            APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {

        String httpMethod = apiGatewayProxyRequestEvent.getHttpMethod();
        String path = apiGatewayProxyRequestEvent.getPath();
        HttpMethod method = HttpMethod.valueOf(httpMethod);
        return new Route(method, path);
    }
}
