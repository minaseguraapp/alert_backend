package co.minasegura.alert.configuration;


import co.minasegura.alert.handler.entrypoint.GetAlertConfigurationLambda;
import co.minasegura.alert.handler.entrypoint.GetAlertLambda;
import co.minasegura.alert.handler.entrypoint.PostAlertLambda;
import co.minasegura.alert.handler.entrypoint.PostConfigureAlertLambda;
import co.minasegura.alert.handler.route.LambdaFunction;
import co.minasegura.alert.handler.route.Route;
import com.amazonaws.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

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
    @Qualifier("alertRouter")
    public Map<Route, LambdaFunction> getAlertRouter(
        GetAlertLambda getAlertLambda,
        PostAlertLambda postAlertLambda
        ) {
        Map<Route, LambdaFunction> routerConfig = new HashMap<>();
        routerConfig.put(new Route(HttpMethod.POST, "/alert"), postAlertLambda);
        routerConfig.put(new Route(HttpMethod.GET, "/alert"), getAlertLambda);
        return routerConfig;
    }

    @Bean
    @Qualifier("alertConfigRouter")
    public Map<Route, LambdaFunction> getAlertConfigRouter(
        GetAlertConfigurationLambda getAlertConfigurationLambda,
        PostConfigureAlertLambda postConfigureAlertLambda
    ) {
        Map<Route, LambdaFunction> routerConfig = new HashMap<>();
        routerConfig.put(new Route(HttpMethod.POST, "/alert/configuration"),
            postConfigureAlertLambda);
        routerConfig.put(new Route(HttpMethod.GET, "/alert/configuration"),
            getAlertConfigurationLambda);
        return routerConfig;
    }

}
