package nagi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerACKs {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //ACKs
        properties.put(ProducerConfig.ACKS_CONFIG, "1");

        //retries
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);

        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(properties);

        stringStringKafkaProducer.send(new ProducerRecord<>("test", "hello"));

        stringStringKafkaProducer.close();
    }
}
