package nagi.kafka;

import nagi.kafka.consumer.ConsumerClient;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KafkaAdminClientTest {
    private KafkaAdminClient kafkaAdminClient;
    private ConsumerClient consumerClient;

    @Before
    public void init() {
        kafkaAdminClient = new KafkaAdminClient();
        kafkaAdminClient.init();
        consumerClient = new ConsumerClient();
        consumerClient.init();
    }

    @Test
    public void deleteRecordsByTopic() {
        System.out.println(kafkaAdminClient.deleteRecordsByTopic("test", consumerClient));
    }

    @Test
    public void deleteRecordsByTime() {
        System.out.println(kafkaAdminClient.deleteRecordsByTime("test", System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(1), consumerClient));
    }

    @Test
    public void getConsumerGroups() {
        kafkaAdminClient.getConsumerGroups();
    }

    @Test
    public void resetGroupOffsetByTopicAndPartition() {
        kafkaAdminClient.resetGroupOffsetByTopicAndPartition("test", "test", 1, 0);
    }

    @Test
    public void resetGroupOffsetByTopic() {
        kafkaAdminClient.resetGroupOffsetByTopic("test", "test", 0, consumerClient);
    }
}
