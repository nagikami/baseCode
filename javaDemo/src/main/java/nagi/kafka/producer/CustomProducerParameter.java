package nagi.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Slf4j
public class CustomProducerParameter {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //缓冲区（accumulator）大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        //批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        //linger.ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        //compress type
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(properties);

        stringStringKafkaProducer.send(new ProducerRecord<>("test", "world"), new Callback() {
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
