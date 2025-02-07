package org.ject.support.external.dynamodb.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.ject.support.domain.tempapply.TemporaryApplication;
import org.springframework.stereotype.Repository;

@Repository
public class TemporaryApplicationRepository extends AbstractDynamoDbRepository<TemporaryApplication> {
    public TemporaryApplicationRepository(final DynamoDbTemplate dynamoDbTemplate) {
        super(dynamoDbTemplate);
    }

    @Override
    void setEntityClass() {
        this.entityClass = TemporaryApplication.class;
    }
}
