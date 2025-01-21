package org.ject.support.testconfig;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface MysqlTestContainer {

    String MYSQL_VERSION = "mysql:8.2";

    @Container
    MySQLContainer<?> container = new MySQLContainer<>(MYSQL_VERSION);
}
