package org.ject.support.external.dynamodb.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.ject.support.domain.tempapply.TemporaryApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@SpringBootTest
class DynamoDbInitializerTest {
    private static final String TABLE_NAME = "temporary_application";
    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Test
    @DisplayName("dynamodb 테이블 초기화")
    void test_initialize_dynamodb() {
        String tableName = dynamoDbEnhancedClient.table(TABLE_NAME,
                TableSchema.fromBean(TemporaryApplication.class)).describeTable().table().tableName();
        assertThat(tableName).isEqualTo(TABLE_NAME);
    }

}
