package org.ject.support.domain.tempapply.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.ject.support.domain.tempapply.domain.TemporaryApplication;
import org.ject.support.external.dynamodb.repository.AbstractDynamoDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemporaryApplicationRepository extends AbstractDynamoDbRepository<TemporaryApplication> {
    public TemporaryApplicationRepository(final DynamoDbTemplate dynamoDbTemplate) {
        super(dynamoDbTemplate);
    }

    @Override
    protected void setEntityClass() {
        entityClass = TemporaryApplication.class;
    }
}
