package nagi.kafka;

import nagi.kafka.consumer.ConsumerClient;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class AdminClientTest {
    private AdminClient adminClient;
    private ConsumerClient consumerClient;

    @Before
    public void init() {
        adminClient = new AdminClient();
        adminClient.init();
        consumerClient = new ConsumerClient();
        consumerClient.init();
    }

    @Test
    public void deleteRecordsByTopic() {
        System.out.println(adminClient.deleteRecordsByTopic("test", consumerClient));
    }

    @Test
    public void deleteRecordsByTime() {
        System.out.println(adminClient.deleteRecordsByTime("test", System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(1), consumerClient));
    }

    @Test
    public void getConsumerGroups() {
        adminClient.getConsumerGroups();
    }

    @Test
    public void resetGroupOffsetByTopicAndPartition() {
        adminClient.resetGroupOffsetByTopicAndPartition("test", "test", 1, 0);
    }

    @Test
    public void resetGroupOffsetByTopic() {
        adminClient.resetGroupOffsetByTopic("test", "test", 0, consumerClient);
    }
}
