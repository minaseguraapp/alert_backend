package co.minasegura.alert.repository.impl;

import co.minasegura.alert.entity.AlertEntity;
import co.minasegura.alert.exception.NotValidParamException;
import co.minasegura.alert.repository.IAlertRepository;
import co.minasegura.alert.util.DynamoDBUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@Repository
public class AlertDynamoDBRepository implements IAlertRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final DynamoDBUtil dynamoUtil;
    private final DynamoDbEnhancedClient enhancedClient;


    public AlertDynamoDBRepository(DynamoDBUtil dynamoUtil,
        DynamoDbEnhancedClient enhancedClient) {
        this.dynamoUtil = dynamoUtil;
        this.enhancedClient = enhancedClient;
    }

    @Override
    public List<AlertEntity> getAlertEntities(String mineId, String zoneId,
        String measurementType) {
        LOGGER.info(
            "Get Alert Repository Started with: MineID[{}] ZoneId[{}] MeasurementType[{}]",
            mineId, zoneId, measurementType);

        if (mineId == null || mineId.isBlank()) {
            throw new NotValidParamException("The mine id is empty");
        }
        DynamoDbTable<AlertEntity> alertTable = enhancedClient.table("AlertTable",
            TableSchema.fromBean(AlertEntity.class));

        if (zoneId != null && !zoneId.isBlank()) {
            return queryTable(mineId, zoneId, measurementType, alertTable);
        } else {
            return queryIndex(mineId, measurementType, alertTable);
        }
    }

    private List<AlertEntity> queryTable(String mineId, String zoneId,
        String measurementType, DynamoDbTable<AlertEntity> alertTable) {

        Key.Builder keyBuilder = Key.builder()
            .partitionValue(dynamoUtil.buildPartitionKey(mineId, zoneId));
        QueryConditional queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());

        if (measurementType != null && !measurementType.isEmpty()) {
            keyBuilder.sortValue(String.format("%s#", measurementType));
            queryConditional = QueryConditional.sortBeginsWith(keyBuilder.build());
        }

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
            .queryConditional(queryConditional)
            .scanIndexForward(false)
            .build();

        return alertTable.query(request)
            .stream()
            .flatMap(page -> page.items().stream())
            .collect(Collectors.toList());
    }

    private List<AlertEntity> queryIndex(String mineId, String measurementType,
        DynamoDbTable<AlertEntity> alertTable) {
        Key.Builder keyBuilder = Key.builder().partitionValue(mineId);

        QueryConditional queryConditional;
        if (measurementType != null && !measurementType.isEmpty()) {
            queryConditional = QueryConditional.sortBeginsWith(
                keyBuilder.sortValue(measurementType).build());
        } else {
            queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());
        }

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
            .queryConditional(queryConditional)
            .scanIndexForward(false)
            .build();

        DynamoDbIndex<AlertEntity> mineTypeIndex = alertTable.index("MineTypeIndex");

        return mineTypeIndex.query(request)
            .stream()
            .flatMap(page -> page.items().stream()).toList();
    }

    @Override
    public boolean createAlert(AlertEntity alertEntity) {
        LOGGER.info("POST Alert Repository");
        DynamoDbTable<AlertEntity> alertTable = enhancedClient.table("AlertEntity",
            TableSchema.fromBean(AlertEntity.class));

        alertTable.putItem(alertEntity);
        return true;
    }
}
