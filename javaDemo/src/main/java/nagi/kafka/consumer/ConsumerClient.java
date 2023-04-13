package nagi.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class ConsumerClient {

    private KafkaConsumer<String, String> kafkaConsumer;

    public void init() {
        //config
        Properties properties = new Properties();

        //connect to server
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");

        //Deserialization
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //set group id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

        //create consumer
        kafkaConsumer = new KafkaConsumer<>(properties);
    }

    public Map<Integer, Long> partitionsWithLatestOffset(String topic) {
        Map<Integer, Long> partitionWithLatestOffset = new HashMap<>();

        List<TopicPartition> partitions = getPartitions(topic);
        partitions.forEach(partition -> {
            long position = getLatestOffset(topic, partition.partition());
            partitionWithLatestOffset.put(partition.partition(), position);
        });

        return partitionWithLatestOffset;
    }

    public Map<Integer, Long> partitionsWithOffsetByTime(String topic, long timestamp) {
        Map<Integer, Long> partitionWithOffsetByTime = new HashMap<>();
        HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();
        List<TopicPartition> partitions = getPartitions(topic);
        partitions.forEach(partition -> topicPartitionLongHashMap.put(partition, timestamp));

        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = kafkaConsumer.offsetsForTimes(topicPartitionLongHashMap);
        topicPartitionOffsetAndTimestampMap.forEach((k, v) -> {
                    long offset;
                    // 当检索不到大于等于时间戳的offset时，客户端会返回null，此时获取最新的offset
                    if (Objects.isNull(v)) {
                        offset = getLatestOffset(topic, k.partition());
                    } else {
                        offset = v.offset();
                    }
                    partitionWithOffsetByTime.put(k.partition(), offset);
                }
        );

        return partitionWithOffsetByTime;
    }

    private long getLatestOffset(String topic, int partition) {
        ArrayList<TopicPartition> topicPartitions = new ArrayList<>();
        topicPartitions.add(new TopicPartition(topic, partition));
        kafkaConsumer.assign(topicPartitions);
        kafkaConsumer.seekToEnd(topicPartitions);
        return kafkaConsumer.position(new TopicPartition(topic, partition));
    }

    public List<TopicPartition> getPartitions(String topic) {
        ArrayList<TopicPartition> topicPartitions = new ArrayList<>();
        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor(topic);
        partitionInfos.forEach(partitionInfo -> topicPartitions.add(new TopicPartition(topic, partitionInfo.partition())));
        return topicPartitions;
    }

    public static void main(String[] args) {
        ConsumerClient kafkaClient = new ConsumerClient();
        kafkaClient.init();
        System.out.println(kafkaClient.partitionsWithLatestOffset("test"));
    }
}
