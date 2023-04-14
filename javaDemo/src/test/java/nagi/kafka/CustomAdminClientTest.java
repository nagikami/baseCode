package nagi.kafka;

import nagi.kafka.consumer.ConsumerClient;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CustomAdminClientTest {
    private CustomAdminClient customAdminClient;
    private ConsumerClient consumerClient;

    @Before
    public void init() {
        customAdminClient = new CustomAdminClient();
        customAdminClient.init();
        consumerClient = new ConsumerClient();
        consumerClient.init();
    }

    @Test
    public void deleteRecordsByTopic() {
        System.out.println(customAdminClient.deleteRecordsByTopic("test", consumerClient));
    }

    @Test
    public void deleteRecordsByTime() {
        System.out.println(customAdminClient.deleteRecordsByTime("test", System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(1), consumerClient));
    }

    @Test
    public void getConsumerGroups() {
        customAdminClient.getConsumerGroups();
    }

    @Test
    public void resetGroupOffsetByTopicAndPartition() {
        customAdminClient.resetGroupOffsetByTopicAndPartition("test", "test", 1, 0);
    }

    @Test
    public void resetGroupOffsetByTopic() {
        customAdminClient.resetGroupOffsetByTopic("test", "test", 0, consumerClient);
    }
}
