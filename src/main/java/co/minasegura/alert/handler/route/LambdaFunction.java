package co.minasegura.alert.handler.route;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface LambdaFunction {
    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent);
}
