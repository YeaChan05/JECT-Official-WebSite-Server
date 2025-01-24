package org.ject.support.domain.test.usecase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestConsumer {
    private final JdbcTemplate jdbcTemplate;
    private static final String sql = "INSERT INTO test (name) VALUES (?)";


    @RabbitListener(queues = "${rabbitmq.queue.name}",concurrency = "10", containerFactory = "prefetchOneContainerFactory")
    public void receiveMessage(List<MessageDto> messages) {
        int[] ints = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, messages.get(i).getMessage());
            }

            @Override
            public int getBatchSize() {
                return messages.size();
            }
        });
    }
}
