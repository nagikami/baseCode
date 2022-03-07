package nagi.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class CustomConsumerSeekTime {
    public static void main(String[] args) {
        //config
        Properties properties = new Properties();

        //connect to server
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");

        //Deserialization
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //set group id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");

        //set consume strategy
        //properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

        //create consumer
        KafkaConsumer<String, String> stringStringKafkaConsumer = new KafkaConsumer<>(properties);

        //subscribe topic
        ArrayList<String> topics = new ArrayList<>();
        topics.add("test");
        stringStringKafkaConsumer.subscribe(topics);

        //获取分区分配方案
        Set<TopicPartition> assignment = stringStringKafkaConsumer.assignment();

        //保证分配方案完成
        while (assignment.size() == 0) {
            stringStringKafkaConsumer.poll(Duration.ofSeconds(1));
            assignment = stringStringKafkaConsumer.assignment();
        }

        //把时间转换为对应的offset
        HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();

        for (TopicPartition topicPartition : assignment) {
            topicPartitionLongHashMap.put(topicPartition, System.currentTimeMillis() - 3 * 24 * 3600 * 1000);
        }

        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = stringStringKafkaConsumer.offsetsForTimes(topicPartitionLongHashMap);

        //指定消费的offset
        for (TopicPartition topicPartition : assignment) {
            OffsetAndTimestamp offsetAndTimestamp = topicPartitionOffsetAndTimestampMap.get(topicPartition);
            stringStringKafkaConsumer.seek(topicPartition, offsetAndTimestamp.offset());
        }

        //consume records
        while (true) {
            ConsumerRecords<String, String> records = stringStringKafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }
}
