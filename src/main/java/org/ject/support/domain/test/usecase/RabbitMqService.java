
package org.ject.support.domain.test.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(MessageDto messageDto) {
//        log.info("messagge send: {}", messageDto.toString());
        this.rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
    }

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void receiveMessage(MessageDto messageDto) {
//        log.info("Received Message : {}", messageDto.toString());
//    }
}
