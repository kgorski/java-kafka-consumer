package kgorski.kafka.consumer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Kafka message Consumer.
 * 
 * @author kgorski
 */
public class KafkaMsgConsumer
{
    /**
     * Application entry point.
     * 
     * @param args the application arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        // Settings
        String topic = "example.topic";
        long waitForMessagesMs = 10000;

        // Get Kafka configuration
        Properties kafkaProperties = new Properties();
        FileInputStream file = new FileInputStream(args[0]);
        kafkaProperties.load(file);
        file.close();

        // Create Kafka Consumer
        Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);

        // Subscribe to topic
        consumer.subscribe(Arrays.asList(topic));

        // Consume messages
        try {
            while (true) {
                // Poll messages
                ConsumerRecords<String, String> messages = consumer.poll(waitForMessagesMs);
                System.out.println(String.format("Polled %d messages", messages.count()));
                if (messages.isEmpty()) {
                    // Poll again
                    continue;
                }

                // Process messages
                for (ConsumerRecord<String, String> message : messages) {
                    System.out.printf("Message: '%s'\ntopic: '%s'\nkey: '%s'\npartition: '%s'\noffset: %d\n\n",
                        message.value(),
                        message.topic(),
                        message.key(),
                        message.partition(),
                        message.offset()
                    );
                }

                // Commit messages
                consumer.commitSync();
            }
        } catch (Exception e) {
            System.out.println("Message consumption failed");
        }

        // Close consumer
        consumer.close();
    }
}
