package com.nagi.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class CustomConsumerPartition {
    public static void main(String[] args) {
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
        KafkaConsumer<String, String> stringStringKafkaConsumer = new KafkaConsumer<>(properties);

        //订阅主题对应的分区
        ArrayList<TopicPartition> topicPartitions = new ArrayList<>();
        topicPartitions.add(new TopicPartition("test", 0));
        stringStringKafkaConsumer.assign(topicPartitions);

        //consume records
        while (true) {
            ConsumerRecords<String, String> records = stringStringKafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }
}
