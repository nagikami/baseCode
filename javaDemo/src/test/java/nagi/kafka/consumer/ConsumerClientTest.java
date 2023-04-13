package nagi.kafka.consumer;

import org.junit.Before;
import org.junit.Test;

public class ConsumerClientTest {
    private ConsumerClient consumerClient;

    @Before
    public void init() {
        consumerClient = new ConsumerClient();
        consumerClient.init();
    }

    @Test
    public void partitionWithLatestOffset() {
        System.out.println(consumerClient.partitionsWithLatestOffset("test"));
    }
}
