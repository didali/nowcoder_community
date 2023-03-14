package com.dida.nowcoder.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@SpringBootTest
public class KafkaTest {

    @Resource
    private KafkaProducer kafkaProducer;

    @Test
    public void testKafka() {
        this.kafkaProducer.sendMessage("test", "你好");
        this.kafkaProducer.sendMessage("test", "在吗");

        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

//生产者
@Component
class KafkaProducer {

    @Resource
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic, String content) {
        this.kafkaTemplate.send(topic, content);
    }
}

//消费者
@Component
class KafkaCustomer {

    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {
        System.out.println(record.value());
    }
}
