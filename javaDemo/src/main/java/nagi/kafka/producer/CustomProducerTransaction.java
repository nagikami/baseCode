package nagi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerTransaction {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //set transaction id
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "abc");

        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(properties);

        stringStringKafkaProducer.initTransactions();

        stringStringKafkaProducer.beginTransaction();

        try{
            stringStringKafkaProducer.send(new ProducerRecord<>("test", "hello"));
            stringStringKafkaProducer.commitTransaction();
        } catch (Exception e) {
            stringStringKafkaProducer.abortTransaction();
        } finally {
            stringStringKafkaProducer.close();
        }
    }
}
