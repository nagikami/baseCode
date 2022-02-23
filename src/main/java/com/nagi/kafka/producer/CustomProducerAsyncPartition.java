package com.nagi.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Slf4j
public class CustomProducerAsyncPartition {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //custom partitioner
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());

        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(properties);

        /**
         * DefaultPartitioner
         * The default partitioning strategy:
         * <ul>
         * <li>If a partition is specified in the record, use it
         * <li>If no partition is specified but a key is present choose a partition based on a hash of the key
         * <li>If no partition or key is present choose the sticky partition that changes when the batch is full.
         *
         * See KIP-480 for details about sticky partitioning.
         */
        stringStringKafkaProducer.send(new ProducerRecord<>("test","a","wa"), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info("Topic: " + metadata.topic() + " Partition: " + metadata.partition());
                }
            }
        });

        stringStringKafkaProducer.close();
    }
}
