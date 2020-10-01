package com.xinghaol.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: lixinghao
 * @date: 2020/9/21 4:29 下午
 * @Description: producer演示
 */
public class ProducerSample {
    private static final String TOPIC_NAME = "xinghaol-java-topic";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Producer<String, String> producer = ProducerSample.getProducer();
        // Producer异步发送
        asyncProducerSend(producer);
        System.out.println("====================分界线====================");
        // Producer异步阻塞发送/ 同步，每次发送后都通过get()方法去获取结果，都会阻塞到那里，所以就叫做同步或者异步阻塞(有Future的关系)
        syncProducerSend(producer);
        System.out.println("====================分界线====================");
        // producer 带回调的发送演示
        asyncProducerCallbackSend(producer);
        // 最后的最后，要将producer关掉
        producer.close();

    }

    /**
     * 获得一个producer，设置一些常用的properties
     *
     * @return
     */
    public static Producer<String, String> getProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.218.128:9092");
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(properties);
    }

    /**
     * Producer异步发送
     *
     * @param producer
     */
    public static void asyncProducerSend(Producer<String, String> producer) {
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord(TOPIC_NAME, "key" + i, "value" + i);
            producer.send(producerRecord);
        }
    }

    /**
     * Producer异步阻塞发送/ 同步，每次发送后都通过get()方法去获取结果，都会阻塞到那里，所以就叫做同步或者异步阻塞(有Future的关系)
     *
     * @param producer
     */
    public static void syncProducerSend(Producer<String, String> producer) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord(TOPIC_NAME, "key" + i, "value" + i);
            Future<RecordMetadata> send = producer.send(producerRecord);
            RecordMetadata recordMetadata = send.get();
            String key = "key-" + i;
            System.out.println(key + ", partition：" + recordMetadata.partition() + ", offset:" + recordMetadata.offset());
        }
    }

    /**
     * producer 带回调的发送演示
     *
     * @param producer
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void asyncProducerCallbackSend(Producer<String, String> producer) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord(TOPIC_NAME, "key" + i, "value" + i);
            String key = "key-" + i;
            producer.send(producerRecord, (metadata, exception) -> {
                System.out.println(key + ", partition：" + metadata.partition() + ", offset:" + metadata.offset());
            });
        }
    }
}
