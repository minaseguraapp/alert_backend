package co.minasegura.alert.configuration;


import co.minasegura.alert.handler.entrypoint.PostConfigureAlertLambda;
import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.handler.route.Route;
import com.amazonaws.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .build();

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public Map<Route, LambdaFunction> getRouter(PostConfigureAlertLambda postConfigureAlertLambda) {
        Map<Route, LambdaFunction> routerConfig = new HashMap<>();
        routerConfig.put(new Route(HttpMethod.POST, "/alert/configure"), postConfigureAlertLambda);
        routerConfig.put(new Route(HttpMethod.POST, "/alert/list"), postConfigureAlertLambda);
        return routerConfig;
    }


}
