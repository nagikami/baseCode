package nagi.kafka;

import lombok.extern.slf4j.Slf4j;
import nagi.kafka.consumer.ConsumerClient;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public class AdminClient {
    private org.apache.kafka.clients.admin.AdminClient adminClient;

    public void init() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.101:9092");
        adminClient = org.apache.kafka.clients.admin.AdminClient.create(properties);
    }

    public boolean deleteRecordsByTopic(String topic, ConsumerClient consumerClient) {
        Map<Integer, Long> partitionWithLatestOffset = consumerClient.partitionsWithLatestOffset(topic);
        return deleteRecords(topic, partitionWithLatestOffset);
    }

    public boolean deleteRecordsByTime(String topic, long timestamp, ConsumerClient consumerClient) {
        Map<Integer, Long> partitionWithLatestOffset = consumerClient.partitionsWithOffsetByTime(topic, timestamp);
        return deleteRecords(topic, partitionWithLatestOffset);
    }

    private boolean deleteRecords(String topic, Map<Integer, Long> partitionWithOffset) {
        Map<TopicPartition, RecordsToDelete> recordsToDelete = new HashMap<>();
        partitionWithOffset.forEach((k, v) -> recordsToDelete.put(new TopicPartition(topic, k), RecordsToDelete.beforeOffset(v)));
        DeleteRecordsResult deleteRecordsResult = adminClient.deleteRecords(recordsToDelete);
        Map<TopicPartition, KafkaFuture<DeletedRecords>> topicPartitionKafkaFutureMap = deleteRecordsResult.lowWatermarks();

        for (Map.Entry<TopicPartition, KafkaFuture<DeletedRecords>> topicPartitionKafkaFutureEntry : topicPartitionKafkaFutureMap.entrySet()) {
            try {
                DeletedRecords deletedRecords = topicPartitionKafkaFutureEntry.getValue().get();
                if (!partitionWithOffset.get(topicPartitionKafkaFutureEntry.getKey().partition()).equals(deletedRecords.lowWatermark())) {
                    log.error("Delete failed, topic: {}, partition: {}, current: {}, expected: {}", topic, topicPartitionKafkaFutureEntry.getKey().partition(), deletedRecords.lowWatermark(), partitionWithOffset.get(topicPartitionKafkaFutureEntry.getKey().partition()));
                    return false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void getConsumerGroups() {
        try {
            List<String> groupIds = adminClient.listConsumerGroups().all().get().stream().map(ConsumerGroupListing::groupId).collect(Collectors.toList());
            Map<String, ConsumerGroupDescription> groupDescriptions = adminClient.describeConsumerGroups(groupIds).all().get();
            groupDescriptions.forEach((k, v) -> {
                log.info("group name: {}", v.groupId());
                v.members().forEach(memberDescription -> {
                    log.info("host: {}", memberDescription.host());
                    Set<TopicPartition> topicPartitions = memberDescription.assignment().topicPartitions();
                    log.info("Partition num: " + topicPartitions.size());
                    topicPartitions.forEach(topicPartition -> log.info("topic name: {}", topicPartition.topic()));
                });
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void resetGroupOffsetByTopicAndPartition(String groupId, String topic, int partition, long offset) {
        HashMap<TopicPartition, OffsetAndMetadata> partitionAndOffset = new HashMap<>();
        partitionAndOffset.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset));
        AlterConsumerGroupOffsetsResult alterConsumerGroupOffsetsResult = adminClient.alterConsumerGroupOffsets(groupId, partitionAndOffset);
        try {
            alterConsumerGroupOffsetsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void resetGroupOffsetByTopic(String groupId, String topic, long offset, ConsumerClient consumerClient) {
        HashMap<TopicPartition, OffsetAndMetadata> partitionAndOffset = new HashMap<>();
        List<TopicPartition> partitions = consumerClient.getPartitions(topic);
        partitions.forEach(partition -> partitionAndOffset.put(partition, new OffsetAndMetadata(offset)));
        AlterConsumerGroupOffsetsResult alterConsumerGroupOffsetsResult = adminClient.alterConsumerGroupOffsets(groupId, partitionAndOffset);
        try {
            alterConsumerGroupOffsetsResult.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
