package co.minasegura.alert.repository.impl;

import co.minasegura.alert.entity.AlertConfigurationEntity;
import co.minasegura.alert.exception.NotValidParamException;
import co.minasegura.alert.repository.IAlertConfigurationRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlertConfigurationDynamoDBRepository implements IAlertConfigurationRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);
    private final DynamoDbEnhancedClient enhancedClient;

    public AlertConfigurationDynamoDBRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @Override
    public List<AlertConfigurationEntity> getAlertConfigurationEntities(@Nonnull String mineId, String measurementType) {
        LOGGER.info(
                "Get Alert Configuration Repository Started with: MineID[{}] MeasurementType[{}]",
                mineId, measurementType);

        if (mineId == null || mineId.isBlank()) {
            throw new NotValidParamException("The mine id is empty");
        }

        final DynamoDbTable<AlertConfigurationEntity> alertConfigurationTable = enhancedClient.table(
                "AlertConfigurationTable", TableSchema.fromBean(AlertConfigurationEntity.class));

        Key.Builder keyBuilder = Key.builder().partitionValue(mineId);

        if (measurementType != null && !measurementType.isEmpty()) {
            keyBuilder.sortValue(measurementType);
        }

        final QueryConditional queryConditional = QueryConditional
                .keyEqualTo(keyBuilder.build());

        return alertConfigurationTable.query(queryConditional).items().stream().collect(Collectors.toList());
    }

    @Override
    public boolean configureAlert(AlertConfigurationEntity alertConfiguration) {
        LOGGER.info("POST Configure Alert Repository");
        DynamoDbTable<AlertConfigurationEntity> alertConfigurationTable = enhancedClient.table("AlertConfigurationTable",
                TableSchema.fromBean(AlertConfigurationEntity.class));

        alertConfigurationTable.putItem(alertConfiguration);
        return true;
    }
}
